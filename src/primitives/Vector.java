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
     *
     * @param scalar
     * @return
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     *
     * @param vector
     * @return
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     *
     * @param vector
     * @return
     */
    public double dotProduct(Vector vector) {
        return (this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3);
    }

    /**
     *
     * @return
     */
    public double lengthSquared() {
        return (this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3);
    }

    /**
     *
     * @return
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *
     * @return
     */
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
