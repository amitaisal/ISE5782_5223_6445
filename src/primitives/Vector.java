package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (xyz.equals(Double3.ZERO)) throw  new IllegalArgumentException("Vector 0");

    }
    public Vector(Double3 xyz) { super(xyz.d1,xyz.d2,xyz.d3);}

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Vector add(Vector edge) {
        return new Vector(xyz.d1+edge.xyz.d1,
                xyz.d2+edge.xyz.d2,
                xyz.d3+edge.xyz.d3);
    }

    public Vector scale(double edge) {
        return new Vector(xyz.d1*edge,
                xyz.d2*edge,
                xyz.d3*edge);
    }

    public Vector crossProduct(Vector edge) {
        return new Vector(this.xyz.d2*edge.xyz.d3-this.xyz.d3*edge.xyz.d2,
                this.xyz.d3*edge.xyz.d1-this.xyz.d1*edge.xyz.d3,
                this.xyz.d1*edge.xyz.d2-this.xyz.d2*edge.xyz.d1);
    }

    public double dotProduct(Vector vector) {

        return this.xyz.d1*vector.xyz.d1+
                this.xyz.d2*vector.xyz.d2+
                this.xyz.d3*vector.xyz.d3;
    }

    public double lengthSquared() {
        return this.xyz.d1*this.xyz.d1+
                this.xyz.d2*this.xyz.d2+
                this.xyz.d3*this.xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double lengthVector=length();
        return new Vector(xyz.d1/lengthVector,xyz.d2/lengthVector,xyz.d3/lengthVector);
    }
}
