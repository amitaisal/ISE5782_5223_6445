package geometries;
import primitives.Point;
import primitives.Vector;

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
}
