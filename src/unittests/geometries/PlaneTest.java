package unittests.geometries;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
                new Point(1, 1, 0)),
                "ERROR: incorrect normal");

        //TC02:The point are on the same line
        assertThrows(IllegalArgumentException.class,()->
                new Plane(new Point(1, 0, 0),
                        new Point(2, 0, 0),
                        new Point(3, 0, 0)),
                "ERROR: incorrect normal");
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
                "ERROR: incorrect normal");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, pl.getNormal(new Point(0, 0, 1)).length(),
                "ERROR: the length of the normal isn't normalized");

        // TC03: check that a given point throws an exception when it's not on the plane
        assertThrows(IllegalArgumentException.class, ()-> pl.getNormal(new Point(0,0,0)),
                "ERROR: point not on the plain doesn't throw an exception");
    }

    @Test
    /**
     * Test method for {@link Plane.FindIntersections}
     */
    void testFindIntersections() {
        Ray ray= new Ray();
        Plane plane=new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        plane.findIntersections();

        assertNull(plane.findIntersections(ray),"");

        assertEquals(List.of(new Point(1,1,1,),plane.findIntersections(ray),"");
    }
}