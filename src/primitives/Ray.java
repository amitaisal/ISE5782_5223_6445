package primitives;

import geometries.Intersectable;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Ray {
    private final Point p0;
    private final Vector dir;
    private static final double DELTA = 0.1;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Ray(Point p0, Vector dir, Vector normal){
        this.dir = dir;
        double nv = alignZero(normal.dotProduct(dir));

        if(isZero(nv))
            this.p0 = p0;
        else {
            Vector deltaVector = normal.scale(nv>0? DELTA : -DELTA);
            this.p0 = p0.add(deltaVector);
        }
    }
    public Point getP0() {
        return p0;
    }


    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double t) { // Function calculate - P = P0 + v * t
        Point p = this.p0.add(this.dir.scale(t));
        return p;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
        if (intersections == null)
            return null;

        GeoPoint closestPoint = intersections.get(0);
        Point point = closestPoint.point;

        double distance = point.distanceSquared(this.p0);

        for (GeoPoint geoPoint : intersections)
        {
            double distanceTemp=geoPoint.point.distanceSquared(this.p0);
            if (distance > distanceTemp)
            {
                closestPoint = geoPoint;
                distance = distanceTemp;
            }
        }
        return closestPoint;
    }

    /***
     *
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    @Override
    public String toString() {
        return "Ray(" + "p0=" + p0 + ", dir=" + dir + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

}
