
package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {


    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * The function receives a ray and returns the color of the closest intersection point of the ray with the scene
     *
     * @param ray The ray that we want to trace.
     * @return The color of the closest point to the camera.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return scene.background;

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);
    }

    /**
     * The function calculates the color of a given point on a given geometry, by calculating the color of the point due to
     * the light sources in the scene, and the color of the point due to the reflected rays from the other geometries in
     * the scene
     *
     * @param geoPoint The point on the surface of the object that was hit by the ray.
     * @param ray the ray that hit the object
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * The function calculates the color of a given point on a given geometry, by adding the emission of the geometry to
     * the local effects of the point (diffuse and specular) and the global effects of the point (reflection and
     * refraction)
     *
     * @param point The point on the geometry that was hit by the ray.
     * @param ray the ray that hit the point
     * @param level the number of recursions
     * @param k the color of the point
     * @return The color of the point.
     */
    public Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {
        if (point ==null)
            return scene.background;
        Color color = point.geometry.getEmission()
                .add(calcLocalEffects(point, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
    }

    /**
     * The function calculates the color of a point on a geometry, by calculating the diffuse and specular components of
     * the light reflected from the point, and adding them together
     *
     * @param gp The intersection point
     * @param ray the ray that hit the geometry
     * @return The color of the point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color =Color.BLACK;
        Vector v=ray.getDir();
        Vector normal=gp.geometry.getNormal(gp.point);
        double nv =alignZero(normal.dotProduct(v));
        if (nv==0)
            return color;
        int nShininess = gp.geometry.getShininess();
        Double3 kd = gp.geometry.getkD(),ks = gp.geometry.getkS();
        for (LightSource lightSource : scene.lights)
        {
            Vector l=lightSource.getL(gp.point);
            double nl =  alignZero(normal.dotProduct(l));
            if (nl * nv >0){
                Double3 ktr = transparency(gp, lightSource,l ,normal);
                if(!ktr.product(INITIAL_K).lowerThan(MIN_CALC_COLOR_K)){
                    Color il = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(calcDiffusive(kd,l,normal,il),
                            calcSpecular(ks,l,normal,v,nShininess,il));
                }
            }
        }
        return color;
    }

    /**
     * The function calculates the color of the point by calculating the color of the reflected ray and the color of the
     * refracted ray, and then adding them to the color of the point
     *
     * @param gp The point on the geometry that the ray intersects with.
     * @param ray the ray that hit the geometry
     * @param level the recursion level.
     * @param k the color of the light that is reflected from the current point.
     * @return The color of the point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,int level, Double3 k){
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.getkR(), kkr = kr.product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point ,ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        Double3 kt = material.getkT(), kkt = kt.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray refractedRay = constructRefractedRay(gp.point, ray, gp.geometry.getNormal(gp.point));
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }


    /**
     * Construct a refracted ray from the given point, ray, and normal.
     *
     * @param point The point of intersection between the ray and the object.
     * @param ray the ray that hit the object
     * @param normal the normal vector of the surface at the point of intersection
     * @return A new ray with the same direction as the original ray, but with a new origin and normal.
     */
    private Ray constructRefractedRay(Point point, Ray ray, Vector normal) {
        return new Ray(point, ray.getDir(), normal);
    }

    /**
     * It finds the closest intersection point of a ray with the scene's geometries
     *
     * @param ray The ray that we want to find the closest intersection to.
     * @return The closest intersection point to the camera.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * The function constructs a reflected ray from a given point and a given ray
     *
     * @param normal The normal vector of the point of intersection.
     * @param point The point where the ray intersects the surface
     * @param inRay The ray that hit the object
     * @return A ray that is reflected from the point of intersection.
     */
    private Ray constructReflectedRay(Vector normal, Point point, Ray inRay) {
        Vector v = inRay.getDir();
        Vector r = v.subtract(normal.scale(alignZero(2 * (normal.dotProduct(v)))));
        return new Ray(point,r.normalize(), normal);
    }

    /**
     * The specular component is the product of the light's color and the material's specular coefficient, raised to the
     * power of the material's shininess
     *
     * @param ks The specular coefficient
     * @param l the light vector
     * @param n normal vector
     * @param v the vector from the point to the camera
     * @param nShininess The shininess of the material.
     * @param il the light's color
     * @return The color of the point on the object.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color il) {
        Vector r = l.subtract(n.scale(alignZero(l.dotProduct(n) * 2)));
        double vr = alignZero(v.scale(-1).dotProduct(r));
        if (vr < 0)
            vr = 0;
        vr = Math.pow(vr, nShininess);
        return il.scale(ks.scale(vr));
    }

    /**
     * > Calculate the diffusive component of the light
     *
     * @param kd Diffuse reflection coefficient
     * @param l the vector from the light source to the point on the object
     * @param n normal vector
     * @param il the color of the light source
     * @return The color of the diffuse light.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n,Color il) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0)
            ln = ln * -1;
        return il.scale(kd.scale(ln));
    }

    /**
     * "If the point is in shadow, return false. Otherwise, return true."
     *
     * The function receives the light source, the point on the geometry, the light vector, the normal vector, and the
     * normal vector's dot product with the vector from the camera to the point
     *
     * @param lightSource The light source that we are checking if it is shaded or not.
     * @param gp The point on the geometry that we're shading.
     * @param l the vector from the light source to the point
     * @param normal the normal vector to the geometry at the intersection point
     * @param nv the dot product of the normal vector and the vector from the light source to the point.
     * @return the color of the pixel.
     */
    private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector normal, double nv) {
        Vector lightDir = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDir, normal);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return true;

        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint geoPoint: intersections) {
            if (geoPoint.point.distance(lightRay.getP0())<lightDistance && geoPoint.geometry.getMaterial().getkT().lowerThan(0))
                return false;
        }
        return true;
    }

    /**
     *
     * @param geoPoint
     * @param lightSource
     * @param dir
     * @param normal
     * @return
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector dir, Vector normal) {
        Vector lightDir = dir.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDir, normal);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        Double3 ktr = new Double3(1.0);

        if (intersections == null)
            return ktr;

        double lightDistance = lightSource.getDistance(lightRay.getP0());

        for (GeoPoint gp: intersections) {
            if(alignZero(gp.point.distance(lightRay.getP0())) < lightDistance){
                ktr = ktr.product(gp.geometry.getMaterial().getkT());
                if(ktr.lowerThan(MIN_CALC_COLOR_K))
                    return new Double3(0.0);
            }
        }
        return ktr;
    }
}
