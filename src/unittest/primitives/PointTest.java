package unittest.primitives;

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
        assertEquals(new Point(1,2,3),point.add(new Vector(1,1,2)));
    }

    @Test
    /**
     * Test method for {@link Point.Subtract}
     */
    void testSubtract() {
        assertEquals(new Vector(0,1,0),point.subtract(new Vector(0,0,1)));

    }

    @Test
    /**
     * Test method for {@link Point.DistanceSquared}
     */
    void testDistanceSquared() {
    }

    @Test
    /**
     * Test method for {@link Point.Distance}
     */
    void testDistance() {
    }
}