package scene;

import lighting.AmbientLight;
import geometries.*;
import primitives.Color;

import java.awt.*;

public class Scene {
    public String sceneName;
    public primitives.Color background ;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene() {
        this.background = new Color(0,0,0);
        this.ambientLight =new AmbientLight();
        this.geometries = new Geometries();
    }

    public Scene(String sceneName) {
        this.sceneName = sceneName;
        this.geometries = new Geometries();
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
         this.ambientLight = ambientLight;
         return this;
    }

    public Scene setBackground(primitives.Color color) {
        this.background = color;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
