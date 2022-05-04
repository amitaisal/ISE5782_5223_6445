package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
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
        for (var light: this.scene.lights) {
            light.getIntensity(geoPoint.point);
        }
        return this.scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcColorEffects(geoPoint,ray));
    }

    /**
     *
     * @param gp
     * @param ray
     * @return
     */
    private Color calcColorEffects(GeoPoint gp, Ray ray) {
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
            if (geoPoint.point.distance(point)<lightDistance)
                return false;
        }
        return true;
    }
}
