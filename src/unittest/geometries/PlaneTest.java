package unittest.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    /**
     * Test method for {@link Plane.constructor}
     */
    void testPlaneConstructor()
    {
        // =============== Boundary Values Tests ==================

        //TC01:The first and second points merge
        assertThrows(IllegalArgumentException.class,()->
                new Plane(new Point(1, 0, 0),
                new Point(1, 0, 0),
                new Point(1, 1, 0)),"");
        //TC02:The point are on the same line
        assertThrows(IllegalArgumentException.class,()->
                new Plane(new Point(1, 0, 0),
                        new Point(2, 0, 0),
                        new Point(3, 0, 0)),"");
    }

    @Test
    /**
     * Test method for {@link Plane.GetNormal}
     */
    void testGetNormal() {

        Plane pl = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        double sqrt = Math.sqrt(1.0/ 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Vector(sqrt, sqrt, sqrt), pl.getNormal(new Point(0, 0, 1)),
                "error incorrect normal");
    }
}