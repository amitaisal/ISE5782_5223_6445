package unittests.geometries;

import geometries.Plane;
import geometries.Sphere;
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
        Ray ray;
        Plane plane = new Plane(new Point(2,0,0),
                new Point(2,1,0),
                new Point(2,1,1));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // **** Group: The Ray is neither orthogonal nor parallel to the plane

        // TC01: The Ray intersects the plane - 1 point
        ray = new Ray(new Point(1,0,0), new Vector(1,0,1));
        result = plane.findIntersections(ray);
        assertEquals(List.of(new Point(2,0,1)), result,
                "ERROR: findIntersections for plane doesn't work properly");
        // TC02: The Ray does not intersect the plane - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(-1,0,1));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane

        // TC11: the ray is included in the plane
        ray = new Ray(new Point(2,-1,0), new Vector(0,1,0));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");
        // TC12: the ray is not included in the plane
        ray = new Ray(new Point(1,0,0), new Vector(0,1,0));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");

        // **** Group: Ray is orthogonal to the plane

        // TC13: p0 is before the plane - 1 point
        ray = new Ray(new Point(1,-1,0), new Vector(1,0,0));
        result = plane.findIntersections(ray);
        assertEquals(List.of(new Point(2,-1,0)), result,
                "ERROR: findIntersections for plane doesn't work properly");
        // TC14: p0 is in the plane - 0 point
        ray = new Ray(new Point(2,-1,0), new Vector(1,0,0));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");
        // TC15: p0 is after the plane - 0 point
        ray = new Ray(new Point(3,-1,0), new Vector(1,0,0));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");

        // **** Group: Ray is neither orthogonal nor parallel to and begins at the plane

        // TC16: p0 is in the plane, but not the ray
        ray = new Ray(new Point(2,0,1), new Vector(1,0,2));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");
        // TC17: p0 is same point which appears as reference point in the plane
        ray = new Ray(new Point(2,0,0), new Vector(1,0,2));
        assertNull(plane.findIntersections(ray),
                "ERROR: findIntersections for plane doesn't work properly");

    }
}