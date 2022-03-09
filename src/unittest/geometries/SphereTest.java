package unittest.geometries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {


    @Test
    /**
     * Test method for {@link Sphere.GetNormal}
     */
    void testGetNormal() {
        Sphere sp= new Sphere(new Point(0,0,0),1);
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,1)), "error incorrect normal");
    }
}