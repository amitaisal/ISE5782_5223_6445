package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 *
 */
public interface Intersectable {

    /**
     * finds the intersections between the geometries and a given ray
     * @param ray
     * @return list of point of intersections with the different geometries. If there are no intersections, returns null.
     */
    public List<Point> findIntersections(Ray ray);



}
