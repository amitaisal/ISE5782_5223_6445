package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

// Creating a new class called SpotLight that extends the PointLight class.
public class SpotLight extends PointLight {

    // Setting the direction of the light and the angle of the light.
    private Vector direction;
    private int angle = 1;

    // The constructor for the SpotLight class. It calls the constructor of the PointLight class and then sets the
    // direction of the light.
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * It returns the intensity of the light source, scaled by the cosine of the angle between the light's direction and
     * the vector from the light source to the point
     *
     * @param point The point on the surface of the object that we want to calculate the intensity of.
     * @return The intensity of the light at a given point.
     */
    @Override
    public Color getIntensity(Point point) {
        Vector dir = super.getL(point);
        double cos = dir.dotProduct(this.direction);
        if(cos < 0)
            cos = 0;
        return super.getIntensity(point).scale(Math.pow(cos,angle));
    }

    /**
     * This function returns the vector from the light source to the center of the geometry
     *
     * @param point The point in space that you want to get the light vector for.
     * @return The vector from the light source to the center of the geometry.
     */
    @Override
    public Vector getL(Point point) {
        return super.getL(point);
    }

    /**
     * Sets the angle of the light's narrow beam and returns the light.
     *
     * @param angle The angle of the spotlight in degrees.
     * @return The SpotLight object.
     */
    public SpotLight setNarrowBeam(int angle) {
        this.angle = angle;
        return this;
    }
}
