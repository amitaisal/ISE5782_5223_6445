package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * @param ray
     * @return
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
     *
     * @param geoPoint
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());

    }
    public Color calcColor(GeoPoint point, Ray ray, int level,Double3 k) {
        if (point ==null)
            return scene.background;
        Color color = point.geometry.getEmission()
                .add(calcLocalEffects(point, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(point, ray, level, k));
    }

    /**
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color =Color.BLACK;
        Vector v=ray.getDir();
        Vector n=gp.geometry.getNormal(gp.point);
        double nv =alignZero(n.dotProduct(v));
        if (nv==0)
            return color;
        int nShininess = gp.geometry.getShininess();
        Double3 kd = gp.geometry.getkD(),ks = gp.geometry.getkS();
        for (LightSource lightSource : scene.lights)
        {
            Vector l=lightSource.getL(gp.point);
            double nl =  alignZero(n.dotProduct(l));
            if (nl * nv >0){
                if(unshaded(lightSource,gp,l,n,nv)){
                    Color il = lightSource.getIntensity(gp.point);
                    color = color.add(calcDiffusive(kd,l,n,il),
                            calcSpecular(ks,l,n,v,nShininess,il));
                }
            }
        }
        return color;
    }

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
            Ray refractedRay = constructRefractedRay(gp.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    private Ray constructRefractedRay( Point point, Ray ray) {
        return new Ray( point,ray.getDir());
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    private Ray constructReflectedRay(Vector normal, Point point, Ray inRay) {
        Vector v = inRay.getDir();
        Vector r = v.subtract(normal.scale(alignZero(2 * (normal.dotProduct(v)))));
        return new Ray(point,r.normalize());
    }


    /**
     *
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param il
     * @return
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
     *
     * @param kd
     * @param l
     * @param n
     * @param il
     * @return
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n,Color il) {
        double ln = alignZero(l.dotProduct(n));
        if (ln < 0)
            ln = ln * -1;
        return il.scale(kd.scale(ln));
    }

    private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n, double nv) {
        Vector lightDir = l.scale(-1); // from point to light source

        Vector deltaVector = n.scale(nv < 0? DELTA : -DELTA);
        Point point = gp.point.add(deltaVector);

        Ray lightRay = new Ray(point, lightDir);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return true;

        double lightDistance = lightSource.getDistance(point);
        for (GeoPoint geoPoint: intersections) {
            if (geoPoint.point.distance(point)<lightDistance && geoPoint.geometry.getMaterial().getkT().lowerThan(0))
                return false;
        }
        return true;
    }
}
