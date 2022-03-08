package unittest.primitives;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, -2, 3);
    Vector v2 = new Vector(2, 3, -4);
    Vector v3 = new Vector(0, 3, 2);

    public VectorTest() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception using 3 doubles constructor");
        } catch (Exception e) {
        }

        try { // test zero vector
            Double3 xyz = new Double3(0, 0, 0);
            new Vector(xyz);
            fail("ERROR: zero vector does not throw an exception using Double3 constructor");
        } catch (Exception e) {
        }
    }

    @org.junit.jupiter.api.Test
    void testAdd() {

        // Test operations with points and vectors
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the add of two vectors is correct.
        assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)),"ERROR: Point + Vector does not work correctly");
        // TC02: Test that subtraction of two vectors is correct.
        assertEquals(new Vector(1, 1, 1), p2.subtract(p1),"ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test that subtraction of the same vector throws an exception
        assertThrows(IllegalArgumentException.class,() -> p1.subtract(p1),
                "add() that result is vector 0 does not throw an exception");
    }

    @org.junit.jupiter.api.Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the testScale() is correct.
        assertEquals(new Vector(6, 9, -12),v2.scale(3),
                "ERROR: the scale doesn't work properly");
        // =============== Boundary Values Tests ==================
        // TC11: Test that testScale() of a vector by 0 throws an exception
        assertThrows(IllegalArgumentException.class,() -> v2.scale(0),
                "ERROR: the scale of a vector by 0, does not throw an exception");
    }

    @org.junit.jupiter.api.Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity). Works only with orthogonal vectors.
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, 4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    @org.junit.jupiter.api.Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot-product is proper
        assertEquals(-16, v1.dotProduct(v2), "dotProduct() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test that dot-product is calculates 0 when vectors are orthogonal
        assertEquals(0, v1.dotProduct(v3), 0.00001,"dotProduct() for orthogonal vectors needs to be zero");
    }

    @org.junit.jupiter.api.Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the lengthSquared() of vector is correct.
        assertTrue(isZero(v1.lengthSquared() - 14),
                "ERROR: lengthSquared() wrong value");
    }

    @org.junit.jupiter.api.Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the length() of vector is correct.
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),
                "ERROR: length() wrong value");
    }

    @org.junit.jupiter.api.Test
    void normalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(u.length() - 1),
                "ERROR: the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");
        assertFalse(v.dotProduct(u) < 0,
                "ERROR: the normalized vector is opposite to the original one");
    }
}