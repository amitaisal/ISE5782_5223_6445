package primitives;

import java.util.Objects;

public class Point {
    protected Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    public Point(Double3 xyz)
    {
        this.xyz = xyz;
    }
    public Point add(Vector vector) {
        return new(vector.xyz.add(this.xyz));
    }

    public Vector subtract(Point point) {
        return new Vector(point.xyz.subtract(this.xyz));
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
