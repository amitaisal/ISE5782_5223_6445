package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;
import static primitives.Util.alignZero;

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

    private Vector rotation(Vector vector, List<Vector> rotate) {
        double x = vector.dotProduct(rotate.get(0));
        double y = vector.dotProduct(rotate.get(1));
        double z = vector.dotProduct(rotate.get(2));
        return new Vector(x,y,z);
    }
}




