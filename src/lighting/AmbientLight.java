
package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;

public class AmbientLight extends Light {

    public AmbientLight() {
        super(Color.BLACK);
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}
