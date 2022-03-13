package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TubeTest {
    @Test
    /**
     * Test method for {@link Tube.GetNormal}
     */
    void testGetNormal() {
        Point point = new Point(0,0,0);
        Vector vector = new Vector(1,0,0);
        Tube tb1 = new Tube(new Ray(point,vector),1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test to check the normal
        assertEquals(new Vector(0,1,0), tb1.getNormal(new Point(1,1,0)),
                "ERROR: incorrect normal in simple test");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, tb1.getNormal(new Point(1,1,0)).length(),
                "ERROR: the length of the normal isn't normalized");

        // TC03: Test to check that we get the correct normal when
        // the dir of the ray is negative
        point = new Point(0,0,1);
        vector = new Vector(0,-1,0);
        Tube tb2 = new Tube(new Ray(point,vector),1);
        assertEquals(new Vector(0,0,1), tb2.getNormal(new Point(0,0.5,2)),
                "ERROR: the normal is incorrect when dir() is negative");

        // =============== Boundary Values Tests ==================
        // TC11: Test to check the normal when it's on the same line
        // of the start of the ray of the tube
        point = new Point(0,0,0);
        vector = new Vector(1,0,0);
        Tube tb3 = new Tube(new Ray(point,vector),1);
        assertEquals(new Vector(0,1,0),tb3.getNormal(new Point(0,1,0)),
                "ERROR: normal is incorrect when it's parallel to p0 of the ray");
    }
}