package unittests.geometries;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point...)}.
     */
    @Test
    public void testGetNormal() {

        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        double xyz = Math.sqrt(1d/ 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test to check the normal
        assertEquals(new Vector(xyz, xyz, xyz), tr.getNormal(new Point(0, 0, 1)),
                "ERROR: incorrect normal of triangle");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, tr.getNormal(new Point(0, 0, 1)).length(),
                "ERROR: the length of the normal isn't normalized");
    }
}