package unittests.renderer;

import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")
				.setAmbientLight(
						new AmbientLight(
								new Color(255, 191, 191),
								new Double3(1,1,1)))
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(
				new Sphere(new Point(0, 0, -250),50), // center
				new Sphere(new Point(250, 0, 0),120),
				new Sphere(new Point(-250, 0, 0),80),
				new Triangle(
						new Point(-100, 0, -250),
						new Point(0, 100, -250),
						new Point(-100, 100, -250)), // up-left
				new Triangle(
						new Point(-100, 0, -250),
						new Point(0, -100, -250),
						new Point(-100, -100, -250)), // down-left
				new Triangle(
						new Point(100, 0, -250),
						new Point(0, -100, -250),
						new Point(100, -100, -250)), // down-right
				new Triangle(
						new Point(-100, 0, 250),
						new Point(0, 100, 250),
						new Point(0, 0, 250)), // up-left
				new Triangle(
						new Point(-100, 0, 250),
						new Point(0, -100, 250),
						new Point(0, 0, 250)), // down-left
				new Triangle(
						new Point(100, 0, 250),
						new Point(0, -100, 250),
						new Point(0, 0, 250)) // down-right
		);


		for (int i = 0; i <= 360*2; i+=10) {
			Camera camera = new Camera(
					Point.ZERO,
					new Vector(0, 0, -1),
					new Vector(0, 1, 0))
					.setVPDistance(250)
					.setVPSize(500, 500)
					.setImageWriter(new ImageWriter("base render test " + i, 1000, 1000))
					.setRayTracer(new RayTracerBasic(scene));
			camera.setRotateZ((double)i/2);
			camera.renderImage();
			camera.printGrid(100, new Color(java.awt.Color.YELLOW));
			camera.setRotateX((double)i);
			camera.writeToImage();
		}
	}

	/**
	 * Test for XML based scene - for bonus
	 *
	@Test
	public void basicRenderXml() {
		Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		// ...

		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500)
				.setImageWriter(new ImageWriter("xml render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));
		camera.printGrid(50, new Color(0,0,0));
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}
	*/
}
