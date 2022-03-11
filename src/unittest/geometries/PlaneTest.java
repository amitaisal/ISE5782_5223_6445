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
        assertThrows(IllegalArgumentException.class,()->
                new Plane(new Point(1, 0, 0),
                new Point(1, 0, 0),
                new Point(1, 1, 0)),"");

        assertThrows(IllegalArgumentException.class,()->
                new Plane(new Point(1, 0, 0),
                        new Point(2, 0, 0),
                        new Point(3, 0, 0)),"");
    }


    // ============ Equivalence Partitions Tests ==============
    @Test
    /**
     * Test method for {@link Plane.GetNormal}
     */
    void testGetNormal() {

        Plane pl = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        double sqrt = Math.sqrt(1.0/ 3);
        assertEquals(new Vector(sqrt, sqrt, sqrt), pl.getNormal(new Point(0, 0, 1)),
                "error incorrect normal");
    }
}