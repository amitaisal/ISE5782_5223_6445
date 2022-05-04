package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point point) {
        double distance = this.position.distanceSquared(point);
        double denominator = kC + kL * Math.sqrt(distance) + kQ * distance;
        return this.getIntensity().scale(1d/denominator);
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
