
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

public class Sphere extends Geometry{

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
        return (point.subtract(center)).normalize();
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){

        GeoPoint geoPoint1;
        GeoPoint geoPoint2;
        if(ray.getP0().equals(this.center))
        {
            Point point=ray.getPoint(this.radius);
            geoPoint1=new GeoPoint(this,point);
            return List.of(geoPoint1);
        }

        Vector u= this.center.subtract(ray.getP0());
        double tm= u.dotProduct(ray.getDir());
        double d= Math.sqrt(u.lengthSquared()-tm*tm);

        if (d >= this.radius)
            return null;
        double th= Math.sqrt(this.radius*this.radius-d*d);
        double t1= tm+th;
        double t2= tm-th;
        if(alignZero(t1) > 0 && alignZero(t2) > 0)
        {
            geoPoint1= new GeoPoint(this, ray.getPoint(t1));
            geoPoint2= new GeoPoint(this, ray.getPoint(t2));
            return List.of(geoPoint1, geoPoint2);
        }
        if (alignZero(t1)>0)
        {
            geoPoint1= new GeoPoint(this, ray.getPoint(t1));
            return List.of(geoPoint1);
        }
        if (alignZero(t2)>0)
        {
            geoPoint1= new GeoPoint(this, ray.getPoint(t2));
            return List.of(geoPoint1);
        }
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