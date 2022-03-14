package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 *
 */
public interface Intersectable {

    /**
     *
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray);



}
