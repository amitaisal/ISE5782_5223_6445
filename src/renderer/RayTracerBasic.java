package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

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
        List<Point> points = new LinkedList<>();
        points = scene.geometries.findIntersections(ray);
        if(points == null)
            return scene.background;

        Point point = ray.findClosestPoint(points);
        return calcColor(point);
    }

    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }


}
