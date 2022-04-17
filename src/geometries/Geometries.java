package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * A composite class to iterate all different geometries
 */
public class Geometries extends Intersectable {

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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> intersections = null;

        for (Intersectable geometry : this.geometries) {
            List<GeoPoint> points = geometry.findGeoIntersections(ray);

            if (points != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();

                intersections.addAll(points);
            }
        }
        return intersections;
    }
}
