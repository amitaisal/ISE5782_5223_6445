
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
            {
                GeoPoint geoPoint = new GeoPoint(this, ray.getPoint(radius));
                return List.of(geoPoint);
            }


            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            GeoPoint geoPoint = new GeoPoint(this, ray.getPoint(t));
            return t == 0 ? null : List.of(geoPoint);
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                GeoPoint geoPoint = new GeoPoint(this, ray.getPoint(t));
                return t == 0 ? null : List.of(geoPoint);
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive

        if (t2 > 0)
        {
            GeoPoint geoPoint1 = new GeoPoint(this, ray.getPoint(t1));
            GeoPoint geoPoint2 = new GeoPoint(this, ray.getPoint(t2));
            return List.of(geoPoint1,geoPoint2);
        }
        else // t2 is behind the head
        {
            GeoPoint geoPoint1 = new GeoPoint(this, ray.getPoint(t1));
            return List.of(geoPoint1);
        }
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
