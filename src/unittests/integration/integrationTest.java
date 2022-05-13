package unittests.integration;

import geometries.Intersectable;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class integrationTest {

    /**
     * It creates a bookshelf with books on it, a window with transparency, and lights it with a few lights
     */
    @Test
    public void bookShelf() {
        Material material = new Material().setKd(0.5).setKs(0.5).setNShininess(30);
        Intersectable shelfTop = new Polygon(
                new Point(500, 300, 0),
                new Point(-500, 300, 0),
                new Point(-500, -300, 0),
                new Point(500, -300, 0))
                .setEmission(new Color(78,53,36).reduce(3d/2))
                .setMaterial(material),
                shelfLeft = new Polygon(
                        new Point(-500, 300, -50),
                        new Point(-500, 300, 0),
                        new Point(-500, -300, 0),
                        new Point(-500, -300, -50))
                        .setEmission(new Color(78,53,36).reduce(3d/2))
                        .setMaterial(material),
                shelfFront = new Polygon(
                        new Point(500, -300, -50),
                        new Point(500, -300, 0),
                        new Point(-500, -300, 0),
                        new Point(-500, -300, -50))
                        .setEmission(new Color(78,53,36).reduce(3d/2))
                        .setMaterial(material),
                left1 = new Polygon(
                        new Point(150, 260, 600),
                        new Point(150, 260, 0),
                        new Point(150, -220, 0),
                        new Point(150, -220, 600))
                        .setEmission(new Color(50,170,80).reduce(3d/2))
                        .setMaterial(material),
                back1 = new Polygon(
                        new Point(150, -220, 600),
                        new Point(150, -220, 0),
                        new Point(250, -220, 0),
                        new Point(250, -220, 600))
                        .setEmission(new Color(50,127,80).reduce(3d/2))
                        .setMaterial(material),
                right1 = new Polygon(
                        new Point(250, 260, 600),
                        new Point(250, 260, 0),
                        new Point(250, -220, 0),
                        new Point(250, -220, 600))
                        .setEmission(new Color(50,170,80).reduce(3d/2))
                        .setMaterial(material),
                top1 = new Polygon(
                        new Point(150, 250, 585),
                        new Point(250, 250, 585),
                        new Point(250, -220, 585),
                        new Point(150, -220, 585))
                        .setEmission(new Color(255,255,255).reduce(3d/2))
                        .setMaterial(material),
                left2 = new Polygon(
                        new Point(255, 260, 700),
                        new Point(255, 260, 0),
                        new Point(255, -260, 0),
                        new Point(255, -260, 700))
                        .setEmission(new Color(150,150,0).reduce(3d/2))
                        .setMaterial(material),
                back2 = new Polygon(
                        new Point(255, -260, 700),
                        new Point(255, -260, 0),
                        new Point(300, -260, 0),
                        new Point(300, -260, 700))
                        .setEmission(new Color(150,150,0).reduce(3d/2))
                        .setMaterial(material),
                right2 = new Polygon(
                        new Point(300, 260, 700),
                        new Point(300, 260, 0),
                        new Point(300, -260, 0),
                        new Point(300, -260, 700))
                        .setEmission(new Color(150,150,0).reduce(3d/2))
                        .setMaterial(material),
                top2 = new Polygon(
                        new Point(255, 260, 690),
                        new Point(300, 260, 690),
                        new Point(300, -260, 690),
                        new Point(255, -260, 690))
                        .setEmission(new Color(255,255,255).reduce(3d/2))
                        .setMaterial(material),
                left3 = new Polygon(
                        new Point(100, 230, 419),
                        new Point(100, -150, 419),
                        new Point(-50, -150, 19),
                        new Point(-50, 230, 19))
                        .setEmission(new Color(220,30,30).reduce(3d/2))
                        .setMaterial(material),
                back3 = new Polygon(
                        new Point(100, -150, 419),
                        new Point(150, -150, 400),
                        new Point(0, -150, 0),
                        new Point(-50, -150, 19))
                        .setEmission(new Color(220,30,30).reduce(3d/2))
                        .setMaterial(material),
                right3 = new Polygon(
                        new Point(150, 230, 400),
                        new Point(150, -150, 400),
                        new Point(0, -150, 0),
                        new Point(0, 230, 0))
                        .setEmission(new Color(220,30,30).reduce(2))
                        .setMaterial(material),
                top3 = new Polygon(
                        new Point(100, 230, 412),
                        new Point(150, 230, 393),
                        new Point(150, -150, 393),
                        new Point(100, -150, 412))
                        .setEmission(new Color(255,255,255).reduce(2))
                        .setMaterial(material),
                wall11 = new Polygon(
                        new Point(-2000,300,1200),
                        new Point(650,300, 1200),
                        new Point(650,300,3000),
                        new Point(-2000,300,3000))
                        .setEmission(new Color(190,130,130).reduce(2))
                        .setMaterial(material),
                wall12 = new Polygon(
                        new Point(-2000,300,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,500),
                        new Point(-2000,300,500))
                        .setEmission(new Color(190,130,130).reduce(2))
                        .setMaterial(material),
                wall13 = new Polygon(
                        new Point(100,300,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,3000),
                        new Point(100,300,3000))
                        .setEmission(new Color(190,130,130).reduce(2))
                        .setMaterial(material),
                wall14 = new Polygon(
                        new Point(-2000,300,-2000),
                        new Point(-650,300, -2000),
                        new Point(-650,300,3000),
                        new Point(-2000,300,3000))
                        .setEmission(new Color(190,130,130).reduce(2))
                        .setMaterial(material),
                window = new Polygon(
                        new Point(100,300,1200),
                        new Point(100,300, 500),
                        new Point(-650,300,500),
                        new Point(-650,300,1200))
                        .setEmission(new Color(200,238,238).reduce(2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100).setKt(0.3)),
                wall2 = new Polygon(
                        new Point(650,-600,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,3000),
                        new Point(650,-600,3000))
                        .setEmission(new Color(210,150,150).reduce(2))
                        .setMaterial(material),
                sphere = new Sphere(
                        new Point(100,600,300),
                        200)
                        .setEmission(new Color(210,150,150).reduce(2))
                        .setMaterial(material);

        Scene scene = new Scene("Test scene bookshelf");
        Camera camera1 = new Camera(
                new Point(-5900,-6000,6150),
                new Vector(1,1,-1),
                new Vector(1,1,2))
                .setVPSize(200, 200).setVPDistance(1500)
                .setRayTracer(new RayTracerBasic(scene));
        //scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0.001));

        scene.geometries.add(shelfTop, shelfLeft, shelfFront,
                left1, back1, right1, top1,
                left2, back2, right2, top2,
                left3, back3, right3, top3,
                sphere,
                wall11, wall12, wall13, wall14, wall2, window);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//
        scene.lights.add(
                new PointLight(
                        new Color(700, 400, 400),
                        new Point(-400, -100, 400))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new PointLight(
                        new Color(500, 300, 300),
                        new Point(100, -400, 700))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new DirectionalLight(
                        new Color(500, 300, 300),
                        new Vector(1, -1, -2)));


        camera1.setImageWriter(new ImageWriter("bookshelf", 600, 600)) //
                .renderImage();
        camera1.writeToImage();
    }
}
