package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Vector add(Vector edge) {
        return null;
    }

    public Vector scale(double edge) {
        return null;
    }

    public Vector crossProduct(Vector edge) {
        return null;
    }

    public double dotProduct(Vector n) {
        return 0.0;
    }

    public double lengthSquared() {
        return 0.0;
    }

    public double length() {
        return 0.0;
    }

    public Vector normalize() {
        return null;
    }
}
