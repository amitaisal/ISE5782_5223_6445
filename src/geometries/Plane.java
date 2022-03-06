package geometries;
import primitives.Point;
import primitives.Vector;

import java.util.Objects;

public class Plane implements Geometry{
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

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + p0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(p0, plane.p0) && Objects.equals(normal, plane.normal);
    }
}
