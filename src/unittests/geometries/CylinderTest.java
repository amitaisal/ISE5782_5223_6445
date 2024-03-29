package unittests.geometries;

import geometries.Cylinder;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    /**
     * Test method for {@link Cylinder.GetNormal}
     */
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Point point = new Point(0,0,0);
        Vector vector = new Vector(1,0,0);
        Ray ray = new Ray(point, vector);
        Cylinder cy1 = new Cylinder(1d, ray, 2d);

        // TC01: Simple single test -when point is on the bottom of the cylinder
        assertEquals(new Vector(1,0,0), cy1.getNormal(new Point(0,1,0)),
                "ERROR: incorrect normal on the bottom of the cylinder\"");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, cy1.getNormal(new Point(0,1,0)).length(),
                "ERROR: the length of the normal isn't normalized");

        point = new Point(0,0,0);
        vector = new Vector(0,0,1);
        ray = new Ray(point, vector);
        Cylinder cy2 = new Cylinder(1d, ray, 1d);

        // TC03: Simple single test -when point is on the top of the cylinder
        assertEquals(new Vector(0,0,1), cy2.getNormal(new Point(0,0.5,1)),
                "ERROR: incorrect normal on the top of the cylinder");

        // TC04: Simple single test -when point is on the surface of the cylinder
        assertEquals(new Vector(0,1,0), cy2.getNormal(new Point(0,0.5,0.5)),
                "ERROR: incorrect normal on the surface of the cylinder");


        // =============== Boundary Values Tests ==================

        // TC11:Single boundary test-when point is on the center bottom of the cylinder
        assertEquals(new Vector(-1,0,0), cy1.getNormal(new Point(0,0,0)),
                "ERROR: incorrect normal to center bottom of cylinder");

        // TC12:Single boundary test-when point is on the center top of the cylinder
        assertEquals(new Vector(1,0,0), cy1.getNormal(new Point(1,0,0)),
                "ERROR: incorrect normal to center top of cylinder");
    }
}