package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import geometries.Intersectable.GeoPoint;

public class RayTracerBasic extends RayTracerBase {

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
        List<GeoPoint> points = scene.geometries.findGeoIntersectionsHelper(ray);
        if(points == null)
            return scene.background;

        GeoPoint point = ray.findClosestGeoPoint(points);
        return calcColor(point);
    }

    private Color calcColor(GeoPoint point) {
        return point.geometry.getEmission();
    }

}
