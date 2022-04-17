package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        Vector dir = super.getL(point);
        double cos = dir.dotProduct(this.direction);
        if(cos < 0)
            cos = 0;
        return super.getIntensity(point).scale(cos);
    }

    @Override
    public Vector getL(Point point) {
        return super.getL(point);
    }

    public SpotLight setNarrowBeam(int i) {
        return this;
    }
}
