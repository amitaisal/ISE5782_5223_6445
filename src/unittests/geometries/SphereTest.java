package unittests.geometries;

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

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test to check the normal
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,1)),
                "ERROR: incorrect normal of sphere");

        // TC02: Test to check that the normal is of length 1
        assertEquals(1d, sp.getNormal(new Point(0, 0, 1)).length(),
                "ERROR: the length of the normal isn't normalized");
    }
}