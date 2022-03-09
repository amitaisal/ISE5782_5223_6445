package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't create vector 0");
    }

    public Vector(Double3 xyz) {
        super(xyz.d1, xyz.d2, xyz.d3);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Can't create vector 0");
    }


    /**
     * adding two vectors together
     * @param vector a given vector
     * @return the new vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * scales the vector
     * @param scalar
     * @return the new vector after scaling it
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * calculates the cross product between two vectors
     * @param vector
     * @return the normal vector
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     * calculates the dot product between two vectors
     * @param vector
     * @return scalar
     */
    public double dotProduct(Vector vector) {
        Double3 xyz = this.xyz.product(vector.xyz);
        return xyz.d1 + xyz.d2 + xyz.d3;
    }

    public double lengthSquared() {
        Double3 xyz = this.xyz.product(this.xyz);
        return xyz.d1 + xyz.d2 + xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return new Vector(this.xyz.reduce(length()));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
