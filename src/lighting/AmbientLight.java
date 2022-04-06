package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;

public class AmbientLight {
    private final Color intensity;

    public AmbientLight() {
        this.intensity = new Color(0,0,0);
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        intensity = Ia.scale(Ka);
    }

    public Color getIntensity() {
        return intensity;
    }
}
