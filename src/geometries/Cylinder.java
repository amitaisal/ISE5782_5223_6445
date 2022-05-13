
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

    /**
     * calculates the normal of a cylinder
     * @param point on the surface of the cylinder
     * @return the normal
     */
    @Override
    public Vector getNormal(Point point) {
        double distance = 0, scalar;
        Point centerPoint;
        Vector vector;

        centerPoint = this.axisRay.getP0();
        if (point.equals(centerPoint))                      // if the point given is the center of the bottom of the cylinder
            return this.axisRay.getDir().scale(-1);         // returns the opposite direction of the ray.

        centerPoint = (this.axisRay.getP0().add(this.axisRay.getDir().scale(this.height)));
        if (point.equals(centerPoint))                      // if the point given is the center of the top of the cylinder
            return this.axisRay.getDir();                   // returns the direction of the ray.

        vector = point.subtract(this.axisRay.getP0());          // vector from p0 to the given point
        scalar = this.axisRay.getDir().dotProduct(vector);      // scalar to calculate the projection

        if(scalar == 0)
            distance = this.axisRay.getP0().distance(point);
        else
            distance = this.axisRay.getP0().add(this.axisRay.getDir().scale(scalar)).distance(point);

        if(distance > this.radius || scalar < 0 || height < scalar)         // not on the cylinder surface
            throw new IllegalArgumentException();

        if(distance <= this.radius && (scalar == height || scalar == 0))    // on the bottom or the top of the cylinder
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
