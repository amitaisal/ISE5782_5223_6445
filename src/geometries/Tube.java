package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

public class Tube implements Geometry{

    protected final Ray axisRay;
    protected final double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
       Vector v = point.subtract(axisRay.getP0());
       double t = v.dotProduct(axisRay.getDir());
       Point o = axisRay.getP0().add(axisRay.getDir().scale(t));
       return point.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return Double.compare(tube.radius, radius) == 0 && Objects.equals(axisRay, tube.axisRay);
    }
}
