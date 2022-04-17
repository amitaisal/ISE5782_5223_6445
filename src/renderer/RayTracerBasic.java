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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return scene.background;

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(GeoPoint geoPoint) {
        double Ka;
        double Kd;
        double Ks;
        double Shininess;
        for (var light: this.scene.lights) {
            light.getIntensity(geoPoint.point);
        }
        return this.scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(this.scene.ambientLight.getIntensity().scale());
    }

}
