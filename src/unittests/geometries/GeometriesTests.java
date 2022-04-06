package unittests.geometries;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTests {

    @Test
    /**
     * Test method for {@link Geometries.FindIntersections}
     */
    void testFindIntersections() {
        Sphere sphere= new Sphere(new Point(2,0,0),0.5);
        Plane plane =new Plane(new Point(3,0,0),new Point(3,1,0),new Point(3,1,1));
        Triangle triangle=  new Triangle(new Point(4,1,0),new Point(4,-1,0),new Point(4,0,3));
        Geometries collection= new Geometries(sphere,plane,triangle);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Some of the Geometries are intersected
        Ray ray= new Ray(new Point(1,0,0), new Vector(6,0,4));
        int num = collection.findIntersections(ray).size();
        assertEquals(2, num,"Bad intersects");

        // =============== Boundary Values Tests ==================
        // TC01: All the Geometries are intersected
        ray= new Ray(new Point(1,0,0), new Vector(6,0,2));
        assertEquals(4,collection.findIntersections(ray).size(),"Bad intersects");

        // TC12: No Geometries are intersected
        ray= new Ray(new Point(1,0,0), new Vector(0,-1,0));
        assertNull(collection.findIntersections(ray),"Bad intersects");

        // TC13: Only one Geometry shape is intersected
        ray= new Ray(new Point(1,0,0), new Vector(1,1,0));
        assertEquals(1,collection.findIntersections(ray).size(),"Bad intersects");
    }
}