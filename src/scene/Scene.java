
package scene;

import lighting.AmbientLight;
import geometries.*;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String sceneName;
    public primitives.Color background ;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<LightSource>();

    public Scene() {
        this.background = new Color(0,0,0);
        this.ambientLight = new AmbientLight();
        this.geometries = new Geometries();
    }

    public Scene(String sceneName) {
        this.background = new Color(0,0,0);
        this.sceneName = sceneName;
        this.geometries = new Geometries();
        this.ambientLight = new AmbientLight();
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setBackground(primitives.Color color) {
        this.background = color;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
