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
            new Point(40,-700,30),
            new Vector(0,1,0),
            new Vector(0,0,1))
            .setVPSize(200, 200)
            .setVPDistance(800)
            .setFocalLength(200)
            .setFocusField(50)
            .setApertureSize(120)
            .setRayTracer(new RayTracerBasic(scene));


    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        Material material = new Material().setKd(0.5).setKs(0.2).setNShininess(30).setKr(0.8);
        scene.geometries.add(
                new Polygon(
                        new Point(-500,500,-40),
                        new Point(500,500,-40),
                        new Point(500,-500,-40),
                        new Point(-500,-500,-40))
                        .setEmission(new Color(java.awt.Color.GRAY).reduce(30)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setNShininess(30)));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        for (int i = 0; i < 5; i++) {
            int min = 50;
            int max = 100;
            int red = (int)Math.floor(Math.random()*(max-min+1)+min);
            int green = (int)Math.floor(Math.random()*(max-min+1)+min);
            int blue = (int)Math.floor(Math.random()*(max-min+1)+min);
            scene.geometries.add(
                    new Sphere(new Point(i*25, i*70, 0), 35d) //
                            .setEmission(new Color(220, 100+green, 100+blue).reduce(3))
                            .setMaterial(material));
        }

        scene.lights.add( //
                new PointLight(new Color(700, 400, 400), new Point(30, -90, 150)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .renderImage();
        camera.writeToImage();
    }
}
