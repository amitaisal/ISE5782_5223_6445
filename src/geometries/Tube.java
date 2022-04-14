package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Tube extends Geometry{

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

    /**
     * calculates the normal of a point on the surface of a tube
     * @param point on the surface of the tube
     * @return the normal at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(axisRay.getP0());
        double scalar = v.dotProduct(axisRay.getDir());
        Point point_o;
        if(scalar == 0)
            point_o = this.axisRay.getP0();
        else
            point_o = axisRay.getP0().add(axisRay.getDir().scale(scalar));
        return point.subtract(point_o).normalize();
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
        return "Tube(" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        return Double.compare(tube.radius, radius) == 0 && Objects.equals(axisRay, tube.axisRay);
    }
}
