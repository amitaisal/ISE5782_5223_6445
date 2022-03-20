package unittests.geometries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SphereTest {


    @Test
    /**
     * Test method for {@link Sphere.GetNormal}
     */
    void testGetNormal() {
        Sphere sp= new Sphere(new Point(0,0,0),1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test to check the normal
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,1)),
                "ERROR: incorrect normal of sphere");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, sp.getNormal(new Point(0, 0, 1)).length(),
                "ERROR: the length of the normal isn't normalized");
    }

    @Test
    /**
     * Test method for {@link Sphere.FindIntersections}
     */
    void testFindIntersections() {
        Sphere sphere= new Sphere(new Point(2,0,0),1d);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere-0 points
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 2, -1))),"Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere-2 points
        Point p1=new Point(1.4,0.8,0);
         Point p2=new Point(2,1,0);
        List<Point> result= sphere.findIntersections(new Ray(new Point(-1,0,0),new Vector(3,1,0)));
        assertEquals(2,result.size(),"Wrong number of points");
        if (result.get(0).getx()>result.get(1).getx())
            result=List.of(result.get(1),result.get(0));
        assertEquals(List.of(p1,p2),result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere-1 point
        result=sphere.findIntersections(new Ray(new Point(1.8, 0.2, 0.2), new Vector(0.2, 0.8, -0.2)));
        assertEquals(1,result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(2,1,0)),result,"Bad intersection point");

        // TC04: Ray starts after the sphere-0 points
        result=sphere.findIntersections(new Ray(new Point(3.5, 1.5, 0), new Vector(3.5, 3.5, 0)));
        assertNull(result,"Wrong number of points");

        // =============== Boundary Values Tests ==================

        //Group1: Ray's line is tangent to the sphere -all tests 0 points
        // TC01: Ray starts before the tangent point
        result=sphere.findIntersections(new Ray(new Point(0,1, 0), new Vector(2, 0, 0)));
        assertNull(result,"Wrong number of points");

        // TC02: Ray starts at the tangent point
        result=sphere.findIntersections(new Ray(new Point(2,1, 0), new Vector(1, 0, 0)));
        assertNull(result,"Wrong number of points");

        // TC03: Ray starts after the tangent point
        result=sphere.findIntersections(new Ray(new Point(3,1, 0), new Vector(1, 0, 0)));
        assertNull(result,"Wrong number of points");

        //Group2: Ray's line crosses the sphere but not the center
        // TC04: Ray starts at sphere and goes inside-1 points
        result = sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(1,-2,0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(2.8,-0.6,0)), result, "Bad intersection point");

        // TC05: Ray starts at sphere and goes outside-0 points
        result=sphere.findIntersections(new Ray(new Point(2,1, 0), new Vector(0.5, 0, 1)));
        assertNull(result,"Wrong number of points");

        // Group: Ray's line goes through the center
        // TC06: Ray starts before the sphere-2 points
        result = sphere.findIntersections(new Ray(new Point(0,0,0), new Vector(1,0,0)));
        if (result.get(0).getx()>result.get(1).getx())
            result=List.of(result.get(1),result.get(0));
        assertEquals(List.of(new Point(1,0,0), new Point(3,0,0)), result, "Bad intersection points");

        // TC07: Ray starts at sphere and goes inside-1 points
        result = sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(3,0,0)), result, "Bad intersection point");

        // TC08: Ray starts inside-1 points
        result = sphere.findIntersections(new Ray(new Point(1.2,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(3,0,0)), result, "Bad intersection point");

        // TC09: Ray starts at the center-1 points
        result = sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(),"Wrong number of points");

        // TC10: Ray starts at sphere and goes outside-0 points
        result = sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        // TC11: Ray starts after sphere-0 points
        result = sphere.findIntersections(new Ray(new Point(4,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        // **** Group: Special cases
        // TC12: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point(4,0,0), new Vector(0,1,0)));
        assertNull(result, "Wrong number of points");
    }
}