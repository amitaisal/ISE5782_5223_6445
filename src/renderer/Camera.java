package renderer;

import primitives.*;
import static primitives.Util.*;

public class Camera {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double distance;
    private double height;
    private double width;

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

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException();
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
        this.p0 = p0;
    }

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRay (int nX, int nY, int j, int i) {
        Point pc = this.p0.add(this.vTo.scale(this.distance));  // refactoring
        double ry = this.height/nY;
        double rx = this.width/nX;

        double xj = (j - alignZero((nX-1)/2.0)) * rx;
        double yi = -1 * (i - alignZero((nY-1)/2.0)) * ry;
        Point pij = pc;
        if (!isZero(xj))
            pij = pij.add(this.vRight.scale(xj));
        if (!isZero(yi))
            pij = pij.add(this.vUp.scale(yi));
        Vector vij = pij.subtract(this.p0);
        return new Ray(this.p0, vij);
    }
}




