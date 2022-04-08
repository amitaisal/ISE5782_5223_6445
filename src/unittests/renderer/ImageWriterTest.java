package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    public int width = 800;
    public int height = 500;
    public int interval = 50;
    ImageWriter imageWriter = new ImageWriter("imageTest", width, height);

    @Test
    void writeToImage() {
        for (int i = 0;i < width; i++) {
            for (int j = 0; j < height;j++) {
                imageWriter.writePixel(i,j,new Color(180,150,100));
            }
        }

        for (int i = 0; i < width; i+=50) {
            for (int j = 0; j < height; j++) {
                imageWriter.writePixel(i,j,new Color(30,30,150));
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j+=50) {
                imageWriter.writePixel(i,j,new Color(30,30,150));
            }
        }
        imageWriter.writeToImage();
    }

    @Test
    void writePixel() {
    }
}