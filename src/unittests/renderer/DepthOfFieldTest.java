package unittests.renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class DepthOfFieldTest {
    Scene scene = new Scene("Test scene bookshelf");
    Camera camera = new Camera(
            new Point(0,-330,110),
            new Vector(0,1,-1d/3),
            new Vector(0,1d/3,1))
            .setVPSize(200, 200)
            .setVPDistance(200)
            .setRayTracer(new RayTracerBasic(scene));


    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        Material material = new Material().setKd(0.5).setKs(0.2).setNShininess(30);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene.geometries.add(
                new Polygon(
                        new Point(-500,500,-40),
                        new Point(500,500,-40),
                        new Point(500,-500,-40),
                        new Point(-500,-500,-40))
                        .setEmission(new Color(java.awt.Color.GRAY)) //
                        .setMaterial(material),
                new Sphere(new Point(0, 0, 0), 40d) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(material),
                new Sphere(new Point(15, 100, 0), 40d) //
                        .setEmission(new Color(java.awt.Color.GREEN)) //
                        .setMaterial(material),
                new Sphere(new Point(-15, -100, 0), 40d) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(material)//
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .renderImage();
        camera.writeToImage();
    }
}
