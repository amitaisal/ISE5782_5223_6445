package scene;

import lighting.AmbientLight;
import geometries.*;

import java.awt.*;

public class Scene {
    public String sceneName;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene() {
        this.background = Color.BLACK;
        AmbientLight ambientLight = this.ambientLight;//= Color.BLACK;
        this.geometries = new Geometries();
    }

    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
         this.ambientLight = ambientLight;
         return this;
    }

    public Scene setBackground(primitives.Color color) {

        return this;
    }
}
