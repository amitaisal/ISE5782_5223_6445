package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A composite class to iterate all different geometries
 */
public class Geometries implements Intersectable {

    private  List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries){
        this.geometries= List.of(geometries);
    }

    /**
     * Adds new geometries to the list of geometries
     * @param geometries list of new geometries
     */
    public void add(Intersectable... geometries){
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * finds all the intersections with all the geometries
     * @param ray
     * @return all the points of intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        if (this.geometries.isEmpty())
            return null;
        List<Point> result = null, points;
        for (Intersectable geometry : this.geometries) {
            points = geometry.findIntersections(ray);
            if (points!=null)
                if (result == null)
                    result = points;
                else
                    result.addAll(points);
        }
        return result;
    }
}
