package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    ImageWriter imageWriter = new ImageWriter("Nerya", 800, 500);

    @Test
    void writeToImage() {
        for (int i = 0;i < 800; i++)
        {
            for (int j = 0; j < 500;j++)
            {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i,j,new Color(30,30,150));
                else
                    imageWriter.writePixel(i,j,new Color(255,255,150));
            }
        }
        imageWriter.writeToImage();
    }

    @Test
    void writePixel() {
    }
}