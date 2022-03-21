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

    public Camera(Point p0, Vector vUp, Vector vTo) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException();
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vUp.crossProduct(vTo).normalize();
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
        return null;
    }
}
