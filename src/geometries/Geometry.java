package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 *
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    public abstract Vector getNormal(Point point);

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

}
