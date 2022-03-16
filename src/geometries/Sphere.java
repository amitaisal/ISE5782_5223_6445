package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Sphere implements Geometry{

    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * calculates the normal of a sphere on a given point
     * @param point on the surface of the sphere
     * @return the normal at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(this.center);
        if(normal.length() != this.radius)
            throw new IllegalArgumentException("The point is not on the surface of the sphere");
        return normal.normalize();
    }

    /**
     *
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray){
        return null;
    }


    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && Objects.equals(center, sphere.center);
    }
}
