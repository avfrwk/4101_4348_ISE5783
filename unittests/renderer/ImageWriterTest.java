package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testing ImageWriter
 */
public class ImageWriterTest {
    @Test
    void firstImageTest() {
        ImageWriter imagewriter = new ImageWriter("firstImage", 800, 500);
        Color backgroundColor = new Color(108, 254, 255);
        Color geridColor = new Color(255, 0, 0);
        // 10 16
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                if (i % (800 / 16) == 0 || j % (500 / 10) == 0) {
                    imagewriter.writePixel(i, j, geridColor);
                } else imagewriter.writePixel(i, j, backgroundColor);

            }
        }
        imagewriter.writeToImage();
    }
}
