package renderer;

import geometries.Plane;
import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.*;
import static primitives.Util.alignZero;

/**
 * The camera class is a class that represents a camera in a 3D scene
 */
public class Camera {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double distance;
    private double height;
    private double width;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numberOfRays = 1;
    private double focusField = 250;
    private double focalLength = 1;
    private double apertureSize = 1;

    // This is the constructor of the camera class. It receives 2 vectors and a point.
    // The first thing it does is to check if the vUp and vTo vectors are orthogonal. If they are not, it throws an
    // exception.
    // Then it normalizes the vectors and calculates the vRight vector.
    // Finally, it sets the p0 point.
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException();
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
        this.p0 = p0;
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getNumberOfRays() {
        return numberOfRays;
    }

    public double getFocusField() {
        return focusField;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public double getApertureSize() {
        return apertureSize;
    }

    /**
     * Sets the distance between the camera and the view plane.
     *
     * @param distance The distance from the camera to the view plane.
     * @return The camera object itself.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the width and height of the view plane.
     *
     * @param width The width of the view plane.
     * @param height The height of the view plane.
     * @return The camera object itself.
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * This function sets the image writer of the camera to the given image writer and returns the camera.
     *
     * @param imageWriter The ImageWriter object that will be used to write the image to a file.
     * @return The camera itself.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * This function sets the ray tracer to the given ray tracer and returns this camera.
     *
     * @param rayTracer The ray tracer to use.
     * @return The camera object itself.
     */
    public Camera setRayTracer(RayTracerBasic rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Camera setNumberOfRays(int numberOfRays) {
        this.numberOfRays = numberOfRays;
        return this;
    }

    public Camera setFocusField(double focusField) {
        this.focusField = this.distance + focusField;
        return this;
    }

    public Camera setFocalLength(double focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    public Camera setApertureSize(double apertureSize) {
        this.apertureSize = apertureSize / 10d;
        return this;
    }

    /**
     * > Construct a ray through pixel (i,j) on the view plane
     *
     * @param nX number of pixels in the horizontal direction
     * @param nY number of pixels in the vertical direction
     * @param j the column index of the pixel in the grid
     * @param i row index
     * @return A ray from the camera to the pixel (i,j)
     */
    public Ray constructRay (int nX, int nY, int j, int i) {
        Point pc = this.p0.add(this.vTo.scale(this.distance));
        Point pij = getCenterPoint(nX, nY, j, i, pc);

        Vector vij = pij.subtract(this.p0);
        return new Ray(this.p0, vij);
    }

    private Point getCenterPoint(int nX, int nY, int j, int i, Point pc) {
        Point pij = pc;
        double ry = alignZero(this.height/ nY);
        double rx = alignZero(this.width/ nX);
        double xj = (j - alignZero((nX -1)/2.0)) * rx;
        double yi = -1 * (i - alignZero((nY -1)/2.0)) * ry;

        if (!isZero(xj))
            pij = pij.add(this.vRight.scale(xj));
        if (!isZero(yi))
            pij = pij.add(this.vUp.scale(yi));
        return pij;
    }

    /**
     * The function constructs a beam of rays that are emitted from the center of a pixel in the screen
     *
     * @param nX number of pixels in the x axis
     * @param nY number of pixels in the y direction
     * @param j the index of the current column
     * @param i the row number of the pixel
     * @return A list of rays.
     */
    private List<Ray> constructBeam(int nX, int nY,  int j, int i) {
        Point pc = this.p0.add(this.vTo.scale(this.distance));
        Point pij = getCenterPoint(nX, nY, j, i, pc);
        Point sPoint;
        Ray sRay;
        double ry = alignZero(this.height/ nY);
        double rx = alignZero(this.width/ nX);
        double randx;
        double randy;
        List<Ray> beam = new LinkedList<>();

        for (int k = 0; k < numberOfRays; k++) {
            randx = random(-rx/2, rx/2);
            randy = random(-ry/2, ry/2);
            sPoint = new Point(pij.getX() + randx, pij.getY() + randy, pij.getZ());
            sRay = new Ray(this.p0, sPoint.subtract(this.p0));
            beam.add(sRay);
        }

        return beam;
    }

    /**
         * > Given a pixel location, Casting a ray from the camera through the pixel at (j,i) and returning the color of the pixel.
         *
         * @param nx the width of the image
         * @param ny the y coordinate of the pixel on the screen
         * @param j the x coordinate of the pixel
         * @param i the row of the pixel
         * @return The color of the pixel at the given coordinates.
         */
    public Color castRay(int nx,int ny,int j,int i) {
        Ray ray = constructRay(nx,ny,j,i);
        return rayTracer.traceRay(ray);
    }

    /**
     * It takes in the pixel coordinates of the pixel to be rendered, and returns the color of that pixel
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the current pixel's x coordinate
     * @return The color of the pixel.
     */
    private Color castRaysAntiAliasing(int nX, int nY, int j, int i) {
        List<Ray> beam = constructBeam(nX,nY,j,i);
        Color colors = Color.BLACK;
        for (Ray ray : beam) {
            colors = colors.add(rayTracer.traceRay(ray));
        }
        return avgColors(colors, beam.size());
    }

    /**
     * Reduce the colors by the size plus one.
     *
     * @param colors The color to average.
     * @param size The size of the kernel.
     * @return The average color of the pixels in the image.
     */
    private Color avgColors(Color colors, double size) {
        return colors.reduce(size + 1);
    }

    /**
     * "Cast a ray from the camera through the pixel at (nX, nY) and return the color of the pixel."
     *
     * The function is called from the main function, which is the function that is called when the program is run
     *
     * @param nX the width of the image
     * @param nY the y coordinate of the pixel in the image
     * @param j the x coordinate of the pixel in the image
     * @param i the current row of the image
     * @return The color of the pixel.
     */
    private Color castRayDepth(int nX, int nY, int j, int i) {
        Ray centerRay = constructRay(nX, nY, j, i);
        Point focalPoint = calcFocalFieldPoint(centerRay);
        return colorSecondaryRays(centerRay, focalPoint);
    }

    /**
     * For each ray, we trace the ray, and then we trace 36 more rays from the aperture center, each rotated by 10 degrees
     *
     * @param centerRay The ray that is being traced from the camera to the pixel.
     * @param focalPoint The point in space that the camera is focused on.
     * @return The color of the pixel.
     */
    private Color colorSecondaryRays(Ray centerRay, Point focalPoint) {
        Color color = rayTracer.traceRay(centerRay);
        Point apertureCenter = centerOfAperture(centerRay);
        int i = 0;
        for (; i < 36; i++){
            Vector v = vUp.rotateVector(vRight, i*10).scale(apertureSize).rotateVector(vUp,i*10);
            Point p = apertureCenter.add(v);
            Ray depthRay = new Ray(p, focalPoint.subtract(p));
            color = color.add(rayTracer.traceRay(depthRay));
        }

        return avgColors(color, i);
    }

    /**
     * > The function calculates the point on the aperture field where the ray intersects the focal plane
     *
     * @param ray the ray that is being traced
     * @return The point on the aperture field that the ray intersects.
     */
    private Point centerOfAperture(Ray ray) {
        double length = this.focalLength / this.vTo.dotProduct(ray.getDir());
        return ray.getPoint(length);
    }

    /**
     * > Given a ray, calculate the point on the focal plane that is the same distance from the focal point as the ray's
     * origin is from the focal point
     *
     * @param centerRay the ray that goes through the center of the lens
     * @return The point on the focal plane that is the same distance from the center of the lens as the focal length.
     */
    private Point calcFocalFieldPoint(Ray centerRay) {
        double length = this.focusField / this.vTo.dotProduct(centerRay.getDir());
        return centerRay.getPoint(length);
    }

    /**
         * The function iterates over the pixels of the image and casts a ray through each pixel
         *
         * @return The camera itself.
         */
    public Camera renderImage() {
        if (this.p0==null||this.vUp==null||this.vTo==null||this.vRight==null||
                this.imageWriter==null||this.rayTracer==null) {
            throw new MissingResourceException("","","");
        }
        final int nX = this.imageWriter.getNx();
        final int nY = this.imageWriter.getNy();

        for (int j = 0; j < nY; j++) {
            for (int i = 0; i < nX; i++) {
                if((this.numberOfRays == 1 && this.apertureSize == 1.0) || checkColor(nX, nY, j, i))
                   imageWriter.writePixel(j,i,castRay(nX,nY ,j,i));
                else if(this.apertureSize != 0)
                   imageWriter.writePixel(j,i,castRayDepth(nX,nY ,j,i));
                else if(this.numberOfRays != 1 )
                    imageWriter.writePixel(j,i,castRaysAntiAliasing(nX,nY ,j,i));
            }
        }
        return this;
    }

    public Camera renderImageThreaded(){
        if (this.p0==null||this.vUp==null||this.vTo==null||this.vRight==null||
                this.imageWriter==null||this.rayTracer==null) {
            throw new MissingResourceException("","","");
        }
        final int nX = this.imageWriter.getNx();
        final int nY = this.imageWriter.getNy();

        IntStream.range(0,nX).parallel().forEach(i->{
            IntStream.range(0,nY).parallel().forEach(j->{
                if((this.numberOfRays == 1 && this.apertureSize == 1.0) || checkColor(nX, nY, j, i))
                    imageWriter.writePixel(j,i,castRay(nX,nY ,j,i));
                else if(this.apertureSize != 0)
                    imageWriter.writePixel(j,i,castRayDepth(nX,nY ,j,i));
                else if(this.numberOfRays != 1 )
                    imageWriter.writePixel(j,i,castRaysAntiAliasing(nX,nY ,j,i));
            });
        });
        return this;
    }

    /**
     * The function checks if the color of the pixel is the same as the color of the pixel in the middle of the pixel
     *
     * @param nX number of pixels in the x axis
     * @param nY number of pixels in the y axis
     * @param j the current column
     * @param i the row of the pixel
     * @return true if the color of the four corners of the pixel are the same.
     */
    private boolean checkColor(int nX, int nY, int j, int i) {

        Point pc = this.p0.add(this.vTo.scale(this.distance));
        Point pij = getCenterPoint(nX, nY, j, i, pc);
        Color topLeft, topRight, bottomLeft, bottomRight;
        double ry = alignZero(this.height/ nY);
        double rx = alignZero(this.width/ nX);

        topLeft = castRay((int)(pij.getX() + -rx/2), (int)(pij.getY() + ry/2), j, i);
        topRight = castRay((int)(pij.getX() + rx/2), (int)(pij.getY() + ry/2), j, i);
        bottomLeft = castRay((int)(pij.getX() + -rx/2), (int)(pij.getY() + -ry/2), j, i);
        bottomRight = castRay((int)(pij.getX() + rx/2), (int)(pij.getY() + -ry/2), j, i);
        return topLeft.equals(topRight) && topLeft.equals(bottomLeft) && topLeft.equals(bottomRight);
    }

    /**
     * If the imageWriter is null, throw an exception, otherwise, write to the image.
     *
     * @return The camera itself.
     */
    public Camera writeToImage() {
        if (this.imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        this.imageWriter.writeToImage();
        return this;
    }

    /**
     * It draws a grid on the image
     *
     * @param interval the interval between the lines
     * @param color the color of the grid lines
     */
    public void printGrid(int interval, Color color) {

        if (this.imageWriter==null)
            throw new MissingResourceException("","","");

        for (int i = 0; i < this.imageWriter.getNx(); i+=interval) {
            for (int j = 0; j < this.imageWriter.getNy(); j++) {
                this.imageWriter.writePixel(i,j,new Color(color.getColor()));
            }
        }

        for (int i = 0; i < this.imageWriter.getNx(); i++) {
            for (int j = 0; j < this.imageWriter.getNy(); j+=interval) {
                this.imageWriter.writePixel(i,j,new Color(color.getColor()));
            }
        }
    }

    /**
     * > Rotate the camera around the X axis by theta degrees
     *
     * @param theta the angle of rotation
     * @return The camera object itself.
     */
    public Camera setRotateX(double theta){
        double radian = -theta * Math.PI / 180;
        double x = vTo.getX();
        double y = vTo.getY();
        double z = vTo.getZ();

        double cos = alignZero(Math.cos(radian));
        double sin = alignZero(Math.sin(radian));
        double Cos = (1-cos);

        Vector rotateX = new Vector(
                x*x*Cos + cos,
                y*x*Cos - z*sin,
                z*x*Cos + y*sin);
        Vector rotateY = new Vector(
                x*y*Cos + z*sin,
                y*y*Cos + cos,
                z*y*Cos - x*sin);
        Vector rotateZ = new Vector(
                x*z*Cos - y*sin,
                y*z*Cos + x*sin,
                z*z*Cos + cos);

        this.vRight = rotation(this.vRight, List.of(rotateX, rotateY, rotateZ));
        this.vUp = rotation(this.vUp, List.of(rotateX, rotateY, rotateZ));

        return this;
    }

    /**
     * > Rotate the camera around the y-axis by theta degrees
     *
     * @param theta the angle of rotation
     * @return The camera object itself.
     */
    public Camera setRotateY(double theta){
        double radian = -theta * Math.PI / 180;
        double x = vRight.getX();
        double y = vRight.getY();
        double z = vRight.getZ();

        double cos = alignZero(Math.cos(radian));
        double sin = alignZero(Math.sin(radian));
        double Cos = (1-cos);

        Vector rotateX = new Vector(
                x*x*Cos + cos,
                y*x*Cos - z*sin,
                z*x*Cos + y*sin);
        Vector rotateY = new Vector(
                x*y*Cos + z*sin,
                y*y*Cos + cos,
                z*y*Cos - x*sin);
        Vector rotateZ = new Vector(
                x*z*Cos - y*sin,
                y*z*Cos + x*sin,
                z*z*Cos + cos);

        this.vTo = rotation(this.vTo, List.of(rotateX, rotateY, rotateZ));
        this.vUp = rotation(this.vUp, List.of(rotateX, rotateY, rotateZ));

        return this;
    }

    /**
     * > Rotate the camera around the Z axis by theta degrees
     *
     * @param theta the angle of rotation
     * @return The camera itself.
     */
    public Camera setRotateZ(double theta){
        double radian = -theta * Math.PI / 180;
        double x = vUp.getX();
        double y = vUp.getY();
        double z = vUp.getZ();

        double cos = alignZero(Math.cos(radian));
        double sin = alignZero(Math.sin(radian));
        double Cos = (1-cos);

        Vector rotateX = new Vector(
                x*x*Cos + cos,
                y*x*Cos - z*sin,
                z*x*Cos + y*sin);
        Vector rotateY = new Vector(
                x*y*Cos + z*sin,
                y*y*Cos + cos,
                z*y*Cos - x*sin);
        Vector rotateZ = new Vector(
                x*z*Cos - y*sin,
                y*z*Cos + x*sin,
                z*z*Cos + cos);

        this.vTo = rotation(this.vTo, List.of(rotateX, rotateY, rotateZ));
        this.vRight = rotation(this.vRight, List.of(rotateX, rotateY, rotateZ));

        return this;
    }

    /**
     * It takes a vector and a list of three vectors, and returns the vector rotated by the three vectors
     *
     * @param vector The vector to rotate
     * @param rotate A list of 3 vectors that represent the rotation matrix.
     * @return A vector that is the result of the rotation.
     */
    private Vector rotation(Vector vector, List<Vector> rotate) {
        double x = vector.dotProduct(rotate.get(0));
        double y = vector.dotProduct(rotate.get(1));
        double z = vector.dotProduct(rotate.get(2));
        return new Vector(x,y,z);
    }
}
