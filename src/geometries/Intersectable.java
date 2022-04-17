package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class Intersectable {

    /**
     * finds the intersections between the geometries and a given ray
     * @param ray
     * @return list of point of intersections with the different geometries. If there are no intersections, returns null.
     */
    public abstract List<Point> findIntersections(Ray ray);

    /**
     *
     */
    public static class GeoPoint {
        /**
         *
         */
        public Geometry geometry;
        /**
         *
         */
        public Point point;

        /**
         *
         * @param geometry
         * @param point
         */

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint(" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    ')';
        }
    }
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
