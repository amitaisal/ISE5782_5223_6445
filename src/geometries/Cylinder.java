package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

public class Cylinder extends Tube{
    private final double height;

    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return super.toString() + ", height=" + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.height, height) == 0;
    }
}
