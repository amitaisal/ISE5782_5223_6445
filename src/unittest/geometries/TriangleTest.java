package unittest.geometries;

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
        double sqrt = Math.sqrt(1.0/ 3);
        assertEquals(new Vector(sqrt, sqrt, sqrt), tr.getNormal(new Point(0, 0, 1)), "error incorrect normal");
    }
}