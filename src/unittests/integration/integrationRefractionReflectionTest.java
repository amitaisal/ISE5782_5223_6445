package unittests.integration;

import geometries.Intersectable;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
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


public class integrationRefractionReflectionTest {

    /**
     * It creates a bookshelf with books on it, a window with transparency, and lights it with a few lights
     */
    @Test
    public void bookShelf() {
        Scene scene = new Scene("Test scene bookshelf");
        Camera camera1 = new Camera(
                new Point(-7500,-7500,3200),
                new Vector(1,1,-1d/2),
                new Vector(1,1,4))
                .setVPSize(300, 300)
                .setVPDistance(1500)
                .setRayTracer(new RayTracerBasic(scene));
        Material wallsMat = new Material().setKd(0.5).setKs(0.2).setNShininess(30);
        Material material = new Material().setKd(0.5).setKs(0.2).setNShininess(30);
        Material bookShelfMat = new Material().setKd(0.2).setKs(0.2).setNShininess(30);
        Material windowMat = new Material().setKd(0.5).setKs(0.5).setNShininess(100).setKt(0.3);
        Color shelfColor = new Color(78, 53, 36).reduce(3d / 2);
        Color wightTop = new Color(java.awt.Color.WHITE).reduce(3d / 2);
        Color lWallColor = new Color(java.awt.Color.PINK).reduce(2);
        Color rWallColor = new Color(java.awt.Color.PINK).reduce(3d / 2);
        Color windowColor = new Color(200, 238, 238).reduce(3d / 2);
        double ceiling = 2450;
        double floor = 0;
        double nWall = -1350;
        double eWall = 1600;
        double wWall = -1600;
        double sWall = 1350;
        double shelfLength = 700;
        double shelfWidth = 350;
        double shelfHeight = 60;
        double leftWindow = nWall * 0.33;
        double rightWindow = sWall * 0.33;
        double topWindow = ceiling * 0.83;
        double bottomWindow = ceiling * 0.37;
        double delta = 15;
        for (int i = 1; i < 5; i++) {
            int interval = 450;
            scene.geometries.add(
                    new Polygon( // shelfTop
                            new Point(sWall, eWall, floor + interval * i),
                            new Point(sWall-shelfLength, eWall, floor + interval * i),
                            new Point(sWall-shelfLength, eWall - shelfWidth, floor + interval * i),
                            new Point(sWall, eWall - shelfWidth, floor + interval * i))
                            .setEmission(shelfColor)
                            .setMaterial(bookShelfMat),
                    new Polygon( // shelfLeft
                            new Point(sWall-shelfLength, eWall, floor + interval * i - shelfHeight),
                            new Point(sWall-shelfLength, eWall, floor + interval * i),
                            new Point(sWall-shelfLength, eWall-shelfWidth, floor + interval * i),
                            new Point(sWall-shelfLength, eWall-shelfWidth, floor + interval * i - shelfHeight))
                            .setEmission(shelfColor)
                            .setMaterial(bookShelfMat),
                    new Polygon( // shelfFront
                            new Point(sWall, eWall-shelfWidth, floor + interval * i - shelfHeight),
                            new Point(sWall, eWall-shelfWidth, floor + interval * i),
                            new Point(sWall-shelfLength, eWall-shelfWidth, floor + interval * i),
                            new Point(sWall-shelfLength, eWall-shelfWidth, floor + interval * i - shelfHeight))
                            .setEmission(shelfColor)
                            .setMaterial(bookShelfMat)
            );
            double lastX = sWall - 600;

            for (int j = 0; j < 9; j++) {
                int min = 0, max = 20;
                int length = (int)Math.floor(Math.random()*(max-min+1)+min) - 220;
                int width = (int)Math.floor(Math.random()*(max-min+1)+min) + 50;
                int height = (int)Math.floor(Math.random()*(max-min+1)+min) + 300;
                min = 10;
                max = 100;
                int red = (int)Math.floor(Math.random()*(max-min+1)+min);
                int green = (int)Math.floor(Math.random()*(max-min+1)+min);
                int blue = (int)Math.floor(Math.random()*(max-min+1)+min);
                Color Color = new Color(50 + red * 2, 70 + green, 70 + blue).reduce(3d / 2);
                double random = Math.random();
                if(random<0.85)
                {
                    scene.geometries.add(
                            new Polygon(
                                    new Point(lastX, eWall, floor + interval * i + height),
                                    new Point(lastX, eWall, floor + interval * i),
                                    new Point(lastX, eWall + length, floor + interval * i),
                                    new Point(lastX, eWall + length, floor + interval * i + height))
                                    .setEmission(Color)
                                    .setMaterial(material),
                            new Polygon(
                                    new Point(lastX, eWall + length, floor + interval * i + height),
                                    new Point(lastX, eWall + length, floor + interval * i),
                                    new Point(lastX + width, eWall + length, floor + interval * i),
                                    new Point(lastX + width, eWall + length, floor + interval * i + height))
                                    .setEmission(Color)
                                    .setMaterial(material),
                            new Polygon(
                                    new Point(lastX + width, eWall, floor + interval * i + height),
                                    new Point(lastX + width, eWall, floor + interval * i),
                                    new Point(lastX + width, eWall + length, floor + interval * i),
                                    new Point(lastX + width, eWall + length, floor + interval * i + height))
                                    .setEmission(Color)
                                    .setMaterial(material),
                            new Polygon(
                                    new Point(lastX, eWall -10, floor + interval * i + height - 15),
                                    new Point(lastX + width, eWall -10, floor + interval * i + height - 15),
                                    new Point(lastX + width, eWall + length, floor + interval * i + height - 15),
                                    new Point(lastX, eWall + length, floor + interval * i + height - 15))
                                    .setEmission(wightTop)
                                    .setMaterial(material)
                    );
                }

                if(lastX <= sWall && j != 8)
                    lastX += width + 5;
                else
                    lastX = sWall;
            }
        }

        for (int i = 0; i < 10; i++) {
            int min = 0, max = 450;
            double lastX = nWall;
            double lastY = wWall;
            double intervalX = 350;
            double intervalY = 270;
            intervalX += (int)Math.floor(Math.random()*(max-min+1)+min);
            min = 0;
            max = 15;
            int red = (int)Math.floor(Math.random()*(max-min+1)+min);
            int green = (int)Math.floor(Math.random()*(max-min+1)+min);
            int blue = (int)Math.floor(Math.random()*(max-min+1)+min);
            Color Color = new Color(160 + red * 2, 40 + green, 40 + blue).reduce(3d / 2);

            for (int j = 0; j < 4; j++) {
                scene.geometries.add(
                        new Polygon(
                                new Point(lastX+5, lastY+5, floor),
                                new Point(lastX+intervalX-5, lastY+5, floor),
                                new Point(lastX+intervalX-5, lastY+intervalY-5, floor),
                                new Point(lastX+5, lastY+intervalY-5, floor))
                                .setEmission(Color)
                                .setMaterial(bookShelfMat));
                if(lastX <= sWall && j != 3)
                    lastX += intervalX;
                else
                    lastX = sWall;
            }
        }
        Intersectable
                wall11 = new Polygon(
                        new Point(nWall,eWall,ceiling),
                        new Point(rightWindow,eWall, ceiling),
                        new Point(rightWindow,eWall, topWindow),
                        new Point(nWall,eWall, topWindow))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall12 = new Polygon(
                        new Point(rightWindow,eWall, ceiling),
                        new Point(sWall,eWall, ceiling),
                        new Point(sWall,eWall,bottomWindow),
                        new Point(rightWindow,eWall,bottomWindow))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall13 = new Polygon(
                        new Point(sWall,eWall,floor),
                        new Point(leftWindow,eWall, floor),
                        new Point(leftWindow,eWall,bottomWindow),
                        new Point(sWall,eWall,bottomWindow))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall14 = new Polygon(
                        new Point(nWall,eWall,floor),
                        new Point(leftWindow,eWall, floor),
                        new Point(leftWindow,eWall,topWindow),
                        new Point(nWall,eWall,topWindow))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                ceiling1 = new Triangle(
                        new Point(nWall,eWall,ceiling),
                        new Point(sWall,eWall,ceiling),
                        new Point((nWall+sWall)/2d,(eWall+wWall)/2d,ceiling*1.5))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                ceiling2 = new Triangle(
                        new Point(sWall,eWall,ceiling),
                        new Point(sWall,wWall,ceiling),
                        new Point((nWall+sWall)/2d,(eWall+wWall)/2d,ceiling*1.5))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                window = new Polygon(
                        new Point(rightWindow,eWall + delta,topWindow),
                        new Point(rightWindow,eWall + delta,bottomWindow),
                        new Point(leftWindow,eWall + delta,bottomWindow),
                        new Point(leftWindow,eWall + delta,topWindow))
                        .setEmission(windowColor)
                        .setMaterial(windowMat),
                frame11 = new Polygon(
                        new Point(rightWindow,eWall + delta,topWindow),
                        new Point(rightWindow,eWall - delta,topWindow),
                        new Point(leftWindow,eWall - delta,topWindow),
                        new Point(leftWindow,eWall + delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame12 = new Polygon(
                        new Point(rightWindow+delta,eWall + delta,topWindow+delta),
                        new Point(rightWindow+delta,eWall - delta,topWindow+delta),
                        new Point(leftWindow,eWall - delta,topWindow+delta),
                        new Point(leftWindow,eWall + delta,topWindow+delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame13 = new Polygon(
                        new Point(rightWindow+delta,eWall - delta,topWindow+delta),
                        new Point(rightWindow+delta,eWall - delta,topWindow),
                        new Point(leftWindow,eWall - delta,topWindow),
                        new Point(leftWindow,eWall - delta,topWindow+delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame21 = new Polygon(
                        new Point(rightWindow,eWall + delta,bottomWindow),
                        new Point(rightWindow,eWall - delta,bottomWindow),
                        new Point(leftWindow,eWall - delta,bottomWindow),
                        new Point(leftWindow,eWall + delta,bottomWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame22 = new Polygon(
                        new Point(rightWindow,eWall + delta,bottomWindow-delta),
                        new Point(rightWindow,eWall - delta,bottomWindow-delta),
                        new Point(leftWindow-delta,eWall - delta,bottomWindow-delta),
                        new Point(leftWindow-delta,eWall + delta,bottomWindow-delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame23 = new Polygon(
                        new Point(rightWindow,eWall - delta,bottomWindow-delta),
                        new Point(rightWindow,eWall - delta,bottomWindow),
                        new Point(leftWindow-delta,eWall - delta,bottomWindow),
                        new Point(leftWindow-delta,eWall - delta,bottomWindow-delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame31 = new Polygon(
                        new Point(rightWindow,eWall + delta,topWindow),
                        new Point(rightWindow,eWall + delta,bottomWindow),
                        new Point(rightWindow,eWall - delta,bottomWindow),
                        new Point(rightWindow,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame32 = new Polygon(
                        new Point(rightWindow+delta,eWall + delta,topWindow),
                        new Point(rightWindow+delta,eWall + delta,bottomWindow-delta),
                        new Point(rightWindow+delta,eWall - delta,bottomWindow-delta),
                        new Point(rightWindow+delta,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame33 = new Polygon(
                        new Point(rightWindow+delta,eWall - delta,topWindow),
                        new Point(rightWindow+delta,eWall - delta,bottomWindow-delta),
                        new Point(rightWindow,eWall - delta,bottomWindow-delta),
                        new Point(rightWindow,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame41 = new Polygon(
                        new Point(leftWindow,eWall + delta,topWindow),
                        new Point(leftWindow,eWall + delta,bottomWindow),
                        new Point(leftWindow,eWall - delta,bottomWindow),
                        new Point(leftWindow,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame42 = new Polygon(
                        new Point(leftWindow-delta,eWall + delta,topWindow+delta),
                        new Point(leftWindow-delta,eWall + delta,bottomWindow),
                        new Point(leftWindow-delta,eWall - delta,bottomWindow),
                        new Point(leftWindow-delta,eWall - delta,topWindow+delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame43 = new Polygon(
                        new Point(leftWindow-delta,eWall - delta,topWindow+delta),
                        new Point(leftWindow-delta,eWall - delta,bottomWindow),
                        new Point(leftWindow,eWall - delta,bottomWindow),
                        new Point(leftWindow,eWall - delta,topWindow+delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame51 = new Polygon(
                        new Point(rightWindow,eWall,(topWindow+bottomWindow)/2d + delta),
                        new Point(rightWindow,eWall - delta, (topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall - delta,(topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall,(topWindow+bottomWindow)/2d + delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame52 = new Polygon(
                        new Point(rightWindow,eWall,(topWindow+bottomWindow)/2d + delta),
                        new Point(rightWindow,eWall + delta, (topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall + delta,(topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall,(topWindow+bottomWindow)/2d + delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame61 = new Polygon(
                        new Point(rightWindow,eWall,(topWindow+bottomWindow)/2d - delta),
                        new Point(rightWindow,eWall - delta, (topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall - delta,(topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall,(topWindow+bottomWindow)/2d - delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame62 = new Polygon(
                        new Point(rightWindow,eWall,(topWindow+bottomWindow)/2d - delta),
                        new Point(rightWindow,eWall + delta, (topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall + delta,(topWindow+bottomWindow)/2d),
                        new Point(leftWindow,eWall,(topWindow+bottomWindow)/2d - delta))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame71 = new Polygon(
                        new Point((leftWindow+rightWindow)/2d - delta,eWall,topWindow),
                        new Point((leftWindow+rightWindow)/2d - delta,eWall,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall - delta,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame72 = new Polygon(
                        new Point((leftWindow+rightWindow)/2d - delta,eWall,topWindow),
                        new Point((leftWindow+rightWindow)/2d - delta,eWall,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall + delta,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall + delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame81 = new Polygon(
                        new Point((leftWindow+rightWindow)/2d,eWall,topWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d + delta,eWall - delta,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d + delta,eWall - delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                frame82 = new Polygon(
                        new Point((leftWindow+rightWindow)/2d,eWall,topWindow),
                        new Point((leftWindow+rightWindow)/2d,eWall,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d + delta,eWall + delta,bottomWindow),
                        new Point((leftWindow+rightWindow)/2d + delta,eWall + delta,topWindow))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                wall2 = new Polygon(
                        new Point(sWall,wWall,floor),
                        new Point(sWall,eWall,floor),
                        new Point(sWall,eWall,ceiling),
                        new Point(sWall,wWall,ceiling))
                        .setEmission(rWallColor)
                        .setMaterial(wallsMat),
                poll11 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d+delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d+delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth+delta*2,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth+delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll21 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d-delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d-delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-delta*2,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll12 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d+delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d+delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth+delta*4,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth+delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),

                poll22 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d-delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d-delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*2,eWall-delta*4,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll13 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d+delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d+delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth+delta*4,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth+delta*4,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll23 = new Polygon(
                        new Point(sWall-shelfLength+delta*2,eWall-shelfWidth/2d-delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d-delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-delta*4,floor),
                        new Point(sWall-shelfLength+delta*2,eWall-delta*4,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll14 = new Polygon(
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d+delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d+delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth+delta*4,floor),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth+delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat),
                poll24 = new Polygon(
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d-delta*2,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-shelfWidth/2d-delta*4,floor + 450 * 4),
                        new Point(sWall-shelfLength+delta*4,eWall-delta*4,floor),
                        new Point(sWall-shelfLength+delta*4,eWall-delta*2,floor))
                        .setEmission(shelfColor)
                        .setMaterial(wallsMat)

        ;

        scene.geometries.add(
                ceiling1, ceiling2,
                frame11, frame12, frame13,
                frame21, frame22, frame23,
                frame31, frame32, frame33,
                frame41, frame42, frame43,
                frame51, frame61, frame71, frame81,
                frame52, frame62, frame72, frame82,
                poll11, poll12, poll13, poll14,
                poll21, poll22, poll23, poll24,
                wall11, wall12, wall13, wall14, wall2, window);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.01));

        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point((nWall+sWall)/2d-delta*2, (eWall+wWall)/2d-delta*2, ceiling * 0.9))
                        .setKl(4E-4).setKq(2E-5));

        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point((nWall+sWall)/2d+delta*2, (eWall+wWall)/2d-delta*2, ceiling * 0.9))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point((nWall+sWall)/2d+delta*2, (eWall+wWall)/2d+delta*2, ceiling * 0.9))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point((nWall+sWall)/2d-delta*2, (eWall+wWall)/2d+delta*2, ceiling * 0.9))
                        .setKl(4E-4).setKq(2E-5));

        scene.lights.add(
                new DirectionalLight(
                        new Color(1200, 1200, 1200),
                        new Vector(1, -1, -2)));


        camera1.setImageWriter(new ImageWriter("bookshelf", 200, 200)) //
                .renderImage();
        camera1.writeToImage();
    }
}
