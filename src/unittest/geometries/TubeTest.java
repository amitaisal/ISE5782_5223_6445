package unittest.geometries;

import geometries.Sphere;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(new Vector(0,1,0), tb1.getNormal(new Point(1,1,0)),
                "error incorrect normal");

        assertEquals(1d, tb1.getNormal(new Point(1,1,0)).length(),
                "error incorrect normal");

        point = new Point(0,0,1);
        vector = new Vector(0,-1,0);
        Tube tb2 = new Tube(new Ray(point,vector),1);
        assertEquals(new Vector(0,0,1), tb2.getNormal(new Point(0,0.5,2)),
                "error incorrect normal");

        assertEquals(1d, tb2.getNormal(new Point(0,0.5,2)).length(),
                "error incorrect normal");

        point = new Point(0,0,0);
        vector = new Vector(1,0,0);
        Tube tb3 = new Tube(new Ray(point,vector),1);
        assertThrows(IllegalArgumentException.class, () -> tb3.getNormal(new Point(0,1,0)),
                "error incorrect normal");
    }
}