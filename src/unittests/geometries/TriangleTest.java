package unittests.geometries;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Test
    public void testFindIntersections() {
        Point p1 = new Point(4,-2,0),
                p2 = new Point(4,2,0),
                p3 = new Point(4,0,3);
        Triangle triangle = new Triangle(p1,p2,p3);
        Ray ray;
        List<Point> result;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test to check the point is in the triangle - 1 point
        ray = new Ray(new Point(1,0,0), new Vector(3,0,1));
        result = triangle.findIntersections(ray);
        assertEquals(List.of(new Point(4,0,1)), result,
                "ERROR: findIntersections for plane doesn't work properly");
        // TC02: Simple test to check the point is outside the triangle opposite the edge - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(3,3,2));
        result = triangle.findIntersections(ray);
        assertNull(result, "ERROR: findIntersections for plane doesn't work properly");
        // TC03: Simple test to check the point is outside the triangle not opposite the edge - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(3,0,4));
        result = triangle.findIntersections(ray);
        assertNull(result, "ERROR: findIntersections for plane doesn't work properly");

        // =============== Boundary Values Tests ==================
        // TC11: the intersection is on the edge of the triangle - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(4,0,0));
        result = triangle.findIntersections(ray);
        assertNull(result, "ERROR: findIntersections for plane doesn't work properly");

        // TC12: the intersection is on the vertex of the triangle - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(3,2,0));
        result = triangle.findIntersections(ray);
        assertNull(result, "ERROR: findIntersections for plane doesn't work properly");

        // TC13: the intersection is on the continues of the edge of the triangle - 0 point
        ray = new Ray(new Point(1,0,0), new Vector(3,3,0));
        result = triangle.findIntersections(ray);
        assertNull(result, "ERROR: findIntersections for plane doesn't work properly");

    }
}