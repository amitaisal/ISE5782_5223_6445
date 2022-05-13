
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight is a Light that is a LightSource.
 */
public class PointLight extends Light implements LightSource {

    // Setting the position of the PointLight.
    private Point position;
    // Setting the default values for the attenuation factors.
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    // The constructor of the PointLight class. It is calling the constructor of the super class (Light) and setting the
    // position of the PointLight.
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the light.
     *
     * @param kC Constant attenuation factor in kc + kl * d + kq * d2
     * @return The object itself.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the constant attenuation factor of the light.
     *
     * @param kL The constant attenuation factor in kc + kl * d + kq * d2.
     * @return The object itself.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the light.
     *
     * @param kQ The attenuation factor for the quadratic term in kc + kl * d + kq * d2.
     * @return The point light object itself.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * The intensity of a point is the intensity of the light source divided by the sum of the constant attenuation, the
     * linear attenuation multiplied by the square root of the distance between the light source and the point, and the
     * quadratic attenuation multiplied by the square of the distance between the light source and the point
     *
     * @param point The point in space to calculate the intensity at.
     * @return The intensity of the light at a given point.
     */
    @Override
    public Color getIntensity(Point point) {
        double disSquared = this.position.distanceSquared(point);
        double denominator = kC + kL * Math.sqrt(disSquared) + kQ * disSquared;
        return this.getIntensity().scale(1d/denominator);
    }

    /**
     * The light vector is the vector from the light source to the point on the surface.
     *
     * @param point The point on the surface of the sphere that we want to find the normal vector of.
     * @return The vector from the point to the center of the sphere.
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(this.position).normalize();
    }

    /**
     * Returns the distance between this point and the given point.
     *
     * @param point The point to get the distance to.
     * @return The distance between the position of the point and the position of the point.
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
