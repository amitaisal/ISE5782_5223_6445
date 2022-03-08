package unittest;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

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
        assertEquals(new Vector(2, 3, 4),new Vector(1, 2, 1).add(new Vector(1, 1, 3)),"ERROR: Point + Vector does not work correctly");
    }

    @org.junit.jupiter.api.Test
    void testScale() {
        assertEquals(new Vector(6, 9, -3),new Vector(2, 3, -1).scale(3),"ERROR: Point + Vector does not work correctly");
    }

    @org.junit.jupiter.api.Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(2, 3, 4);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    @org.junit.jupiter.api.Test
    void dotProduct() {
    }

    @org.junit.jupiter.api.Test
    void lengthSquared() {
    }

    @org.junit.jupiter.api.Test
    void length() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // test length..
        if (!isZero(v1.lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");
    }

    @org.junit.jupiter.api.Test
    void normalize() {
    }
}