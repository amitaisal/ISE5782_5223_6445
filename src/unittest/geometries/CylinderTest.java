package unittest.geometries;

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

        assertEquals(new Vector(1,0,0), cy1.getNormal(new Point(0,1,0)),
                "");

        point = new Point(0,0,0);
        vector = new Vector(0,0,1);
        ray = new Ray(point, vector);
        Cylinder cy2 = new Cylinder(1d, ray, 1d);

        assertEquals(new Vector(0,0,1), cy2.getNormal(new Point(0,0.5,1)),
                "");

        assertEquals(new Vector(0,1,0), cy2.getNormal(new Point(0,0.5,0.5)),
                "");

        assertEquals(new Vector(0,0,1), cy2.getNormal(new Point(0,0.5,0)),
                "");

        // =============== Boundary Values Tests ==================

        assertEquals(new Vector(-1,0,0), cy1.getNormal(new Point(0,0,0)),"");

        assertEquals(new Vector(1,0,0), cy1.getNormal(new Point(1,0,0)),"");




    }
}