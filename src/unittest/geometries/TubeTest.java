package unittest.geometries;

import geometries.Sphere;
import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {


    @Test
    /**
     * Test method for {@link Tube.GetNormal}
     */
    void testGetNormal() {

        Tube tb= new Tube(new Ray(new Point(0,0,0),new Vector(1,0,0)),1);
        assertEquals(new Vector(0,0,1),tb.getNormal(new Point(1,0,0)), "error incorrect normal");
        assertEquals(new Vector(0,0,1),tb.getNormal(new Point(0,1,0)), "error incorrect normal");
    }
}