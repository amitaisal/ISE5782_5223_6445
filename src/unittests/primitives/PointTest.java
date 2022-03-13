package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for primitive.Point class
 */
public class PointTest {
    Point point = new Point(0,1,1);

    @Test
    /**
     * Test method for {@link Point.Add}
     */
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the add() works correctly
        assertEquals(new Point(1,2,3),point.add(new Vector(1,1,2)));
    }

    @Test
    /**
     * Test method for {@link Point.Subtract}
     */
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the subtract() works correctly
        assertEquals(new Vector(0,1,0),point.subtract(new Vector(0,0,1)));
    }

    @Test
    /**
     * Test method for {@link Point.DistanceSquared}
     */
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distanceSquared() works correctly
        assertEquals(10.25d, point.distanceSquared(new Point(1,-2,0.5)), "");
    }

    @Test
    /**
     * Test method for {@link Point.Distance}
     */
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the distance() works correctly
        assertEquals(Math.sqrt(10.25d), point.distance(new Point(1,-2,0.5)), "");
    }
}