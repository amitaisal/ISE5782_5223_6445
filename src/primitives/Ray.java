package primitives;

import geometries.Intersectable;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

public class Ray {
    private final Point p0;
    private final Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }


    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double t) { // Function calculate - P = P0 + v * t
        Point p = p0.add(dir.scale(t));
        return p;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> Points){
        return null;
    }

    /***
     *
     */
    public Point findClosestPoint(List<Point> points)
    {
        if (points==null)
            return null;
        Point closestPoint=points.get(0);
        double distance = closestPoint.distanceSquared(this.p0);

        for (Point point : points)
        {
            double distanceTemp=point.distanceSquared(this.p0);
            if (distance > distanceTemp)
            {
                closestPoint=point;
                distance=distanceTemp;
            }
        }
        return closestPoint;
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
