package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon {

    public Triangle(Point... vertices) {
        super(vertices);
    }

    public List<Point> findIntersections(Ray ray){
        List<Point> result = plane.findIntersections(ray);
        if (result==null) // In case there is no intersection with the plane return null
            return null;
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray .getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        double d1 = ray.getDir().dotProduct(n1);
        double d2 = ray.getDir().dotProduct(n2);
        double d3 = ray.getDir().dotProduct(n3);

        if((d1 < 0 && d2 < 0 && d3 < 0) || (d1 > 0 && d2 > 0 && d3 > 0))  // In case the all scalars are in the same sign,
            return result;// the point is in the triangle
        return null;
    }
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){

        List<GeoPoint> result = plane.findGeoIntersectionsHelper(ray);
        if (result==null) // In case there is no intersection with the plane return null
            return null;
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray .getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        double d1 = ray.getDir().dotProduct(n1);
        double d2 = ray.getDir().dotProduct(n2);
        double d3 = ray.getDir().dotProduct(n3);

        if((d1 < 0 && d2 < 0 && d3 < 0) || (d1 > 0 && d2 > 0 && d3 > 0))  // In case the all scalars are in the same sign,
            return result;// the point is in the triangle
        return null;
    }


    @Override
    public String toString() {
        return "Triangle(" +
                "vertices=" + vertices+
                ", plane=" + plane.toString() +
                ')';
    }
}
