package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;
import java.util.Objects;

/**
 * Plane class represents a plane using a point and a normal vector
 */
public class Plane extends Geometry{

    private final Point p0;
    private final Vector normal;

    public Plane(Point p0, Point p1, Point p2) {
        Vector vector1 = p0.subtract(p1);
        Vector vector2 = p0.subtract(p2);
        normal = vector1.crossProduct(vector2).normalize();
        this.p0 = p0;
    }

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * calculates the normal of a plane
     * @param point on the plane
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) {
        if(!point.equals(this.p0))
        {
            Vector vector = point.subtract(this.p0);
            double angle = vector.dotProduct(this.normal);
            if(angle != 0)
                throw new IllegalArgumentException("The point is not on the plain");
        }
        return normal;
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){

        double numerator, denominator;

        if(this.p0.equals(ray.getP0()))
            return null;
        // normal dot (Q - p0)
        numerator = this.normal.dotProduct(this.p0.subtract(ray.getP0()));
        // normal dot dir of ray
        denominator = this.normal.dotProduct(ray.getDir());

        if(isZero(denominator))
            return null;

        double t = alignZero(numerator / denominator);

        // if t is positive it means the dir is towards the plane
        // and there is an intersection point
        if (t > 0)
        {
            Point point =  ray.getP0().add(ray.getDir().scale(t));
            GeoPoint geoPoint =new GeoPoint(this,point);
            return List.of(geoPoint);
        }
        return null;
    }


    @Override
    public String toString() {
        return "Plane(" +
                "p0=" + p0 +
                ", normal=" + normal +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(p0, plane.p0) && Objects.equals(normal, plane.normal);
    }
}
