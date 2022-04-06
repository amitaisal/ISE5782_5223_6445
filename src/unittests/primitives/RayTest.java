package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    /**
     * Test method for {@link Ray.FindClosestPoint}
     */
    void testFindClosestPoint() {
        Point p1 = new Point(3,0,2);
        Point p2 = new Point(4,0,3);
        Point p3 = new Point(5,0,4);
        Ray ray= new Ray (new Point(1,0,0),new Vector(1,0,1));

        // ============ Equivalence Partitions Tests ==============
        //TC01:
        List<Point> points=List.of(p2,p1,p3);
        assertEquals(p1,ray.findClosestPoint(points),"" );

        // =============== Boundary Values Tests ==================
        //TC01:
        points=null;
        assertNull(ray.findClosestPoint(points),"" );

        //TC02:
        points=List.of(p1,p2,p3);
        assertEquals(p1,ray.findClosestPoint(points),"" );

        //TC03:
        points=List.of(p2,p3,p1);
        assertEquals(p1,ray.findClosestPoint(points),"" );
    }
}