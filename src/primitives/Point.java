package primitives;

import java.util.Objects;

public class Point {

    final protected Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }
    public Point(Double3 xyz)
    {
        this.xyz = xyz;
    }

    public Point add(Vector vector) {
        return  new Point(this.xyz.add(vector.xyz));
    }
    public Vector subtract(Point point) {
        return new Vector(this.xyz.subtract(point.xyz));
    }
    public double distanceSquared(Point point){
        return (point.xyz.d1*point.xyz.d1+point.xyz.d2*point.xyz.d1+point.xyz.d3*point.xyz.d1)
                -(this.xyz.d1*this.xyz.d1+this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3);
    }
    public double distance (Point point){
        return Math.sqrt(this.distanceSquared(point));
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }
}
