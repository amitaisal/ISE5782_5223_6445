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

        if (point.equals(this.axisRay.getP0()))
            return this.axisRay.getDir().scale(-1);
        Point p1=(this.axisRay.getP0().add(this.axisRay.getDir().scale(this.height)));
        if (point.equals(p1))
            return this.axisRay.getDir();

        Vector v3 = point.subtract(this.axisRay.getP0());
        double t = this.axisRay.getDir().dotProduct(v3);
        double distance = 0;
        if(t == 0)
            distance = this.axisRay.getP0().distance(point);
        else
            distance = this.axisRay.getP0().add(this.axisRay.getDir().scale(t)).distance(point);
        if(distance > this.radius)
            throw new IllegalArgumentException();
        if(t < 0 || t > height)
            throw new IllegalArgumentException();
        if(distance < this.radius && (t == height || t == 0))
            return axisRay.getDir();
        else
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
