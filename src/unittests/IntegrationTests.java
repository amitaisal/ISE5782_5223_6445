package unittests;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.Camera;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {
    static final Point ZERO_POINT = new Point(0, 0, 0);
    Camera camera = new Camera(ZERO_POINT,
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setVPSize(3,3)
            .setVPDistance(2);
    LinkedList<Ray> rayList = findRaysThroughVpPixels(camera, 3, 3);
    Sphere sphere;
    Plane plane;
    Triangle triangle;
    
    String sphereErrorMessage = "ERROR: Wrong number of intersections of camera rays with sphere";
    String planeErrorMessage = "ERROR: Wrong number of intersections of camera rays with plane";
    String triangleErrorMessage = "ERROR: Wrong number of intersections of camera rays with triangle";

    /**
     * Tests the camera rays and sphere intersections
     */
    @Test
    public void testCameraAndSphere() {
        // **** Group: Sphere&Camera integration test cases ****//
        // #1: Camera rays intersects 2 points with sphere
        sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2,countIntersections(rayList, sphere), sphereErrorMessage);

        // #2: Camera rays intersects 18 points with sphere
        sphere = new Sphere(new Point(0, 0, -3), 2);
        assertEquals(18,countIntersections(rayList, sphere),sphereErrorMessage);

        // #3: Camera rays intersects 10 points with sphere
        sphere = new Sphere(new Point(0, 0, -3), 1.5);
        assertEquals(10,countIntersections(rayList, sphere),sphereErrorMessage);

        // #4: Camera rays intersects 9 points with sphere
        sphere = new Sphere(new Point(0, 0, -3), 4);
        assertEquals(9,countIntersections(rayList, sphere),sphereErrorMessage);

        // #5: No camera rays intersection with sphere
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0,countIntersections(rayList, sphere),sphereErrorMessage);

    }
    /**
     * Tests the camera rays and plane intersections
     */
    @Test
    public void testCameraAndPlane() {
        // **** Group: Plane&Camera integration test cases ****//
        // #11: Camera intersects 9 points with plan
        plane = new Plane(new Point(0, 0, -4), camera.getvTo());
        assertEquals( 9, countIntersections(rayList, plane),planeErrorMessage);

        // #12: Camera rays intersects 9 points with plan
        plane = new Plane(new Point(0, 0, -4), new Vector(new Double3(0, -0.5, 1)));
        assertEquals( 9, countIntersections(rayList, plane),planeErrorMessage);

        // #13: Camera rays intersects 6 points with plan
        plane = new Plane(new Point(0, 0, -4), new Vector(new Double3(0, -2, 1)));
        assertEquals( 6, countIntersections(rayList, plane),planeErrorMessage);
    }

    /**
     * Tests the camera rays and triangle intersections
     */
    @Test
    public void testCameraAndTriangle() {
        // **** Group: Triangle&Camera integration test cases ****//
        // #21: Camera rays intersects 1 points with triangle
        triangle = new Triangle(new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertEquals( 1, countIntersections(rayList, triangle),triangleErrorMessage);

        // #22: Camera rays intersects 2 points with triangle
        triangle = new Triangle(new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        assertEquals(2, countIntersections(rayList, triangle),triangleErrorMessage);
    }
    /**
     * Finds and sums up the number of camera rays that intersect with a given shape
     *
     * @param rayList
     * @param shape
     * @return Number of intersections of the camera rays with a given shape
     */
    private int countIntersections(LinkedList<Ray> rayList, Intersectable shape) {
        int counter=0;
        for(Ray ray: rayList)
        {
            List<Point> result=shape.findIntersections(ray);
            if (result!=null)
                counter+=result.size();
        }
        return counter;
    }

    /**
     * Calculates with loop all the rays from camera through middle of each pixel
     *
     * @param camera
     * @param nX number of pixels in x
     * @param nY number of pixels in y
     * @return List of rays from camera through pixels
     */
    public LinkedList<Ray> findRaysThroughVpPixels(Camera camera, int nX, int nY) {
        LinkedList<Ray> raysList = new LinkedList<>();
        // For each pixel calls constructRay()
        for (int j = 0; j < nY; j++) {
            for (int i = 0; i < nX; i++) {
                raysList.add(camera.constructRay(nX, nY, j, i));
            }
        }
        return raysList;
    }
}


