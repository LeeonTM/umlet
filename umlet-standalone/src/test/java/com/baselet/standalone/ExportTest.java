package com.baselet.standalone;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExportTest {

    static String TEST_FILE_LOCATION;
    static File INPUT_FILE;

    @BeforeClass
    public static void beforeClass() throws URISyntaxException {
        TEST_FILE_LOCATION = MainBatchmodeTest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getSchemeSpecificPart() + MainBatchmodeTest.class.getCanonicalName().replace(".", "/").replace(MainBatchmodeTest.class.getSimpleName(), "");
        TEST_FILE_LOCATION += "our";
        INPUT_FILE = new File(TEST_FILE_LOCATION + "\\input_convertToPngTest.uxf");
    }

    @Test
    public void testExportToPng() throws IOException {
        String output = TEST_FILE_LOCATION + "/testConvertToPng";

        MainStandalone.main(new String[] {
                "-action=convert",
                "-format=png",
                "-filename=" + INPUT_FILE.getAbsolutePath(),
                "-output=" + output});

        File outputFile = new File(output + ".png");
        File exampleFile = new File(TEST_FILE_LOCATION + "\\input_convertToPngTest.png");

        assertTrue(outputFile.exists());
        assertImageEqual(outputFile, exampleFile);
    }

    @Test
    public void testExportToNonType() {
        String output = TEST_FILE_LOCATION + "/testConvertToNonType";

        try {
            MainStandalone.main(new String[] {
                    "-action=convert",
                    "-format=gjtkyh",
                    "-filename=" + INPUT_FILE.getAbsolutePath(),
                    "-output=" + output});
        } catch (Exception ex) {
            assertTrue(ex.getMessage() == "gjtkyh is an invalid format");
        }
    }

    @Test
    public void testExportInvalidInput() {
        File input = new File(TEST_FILE_LOCATION + "\\input_NonInput.uxf");
        String output = TEST_FILE_LOCATION + "/testInvalidInput";

        MainStandalone.main(new String[] {
                "-action=convert",
                "-format=png",
                "-filename=" + input.getAbsolutePath(),
                "-output=" + output});

        assertFalse(new File (output + ".png").exists());
    }

    private void assertImageEqual(File actual, File expected) throws IOException {
        BufferedImage expectedImage = ImageIO.read(expected);
        Integer expectedHeight = expectedImage.getHeight();
        Integer expectedWidth = expectedImage.getWidth();

        // 1. check image size
        BufferedImage actualImage = ImageIO.read(actual);
        String expSize = Integer.toString(expectedWidth) + "x" + Integer.toString(expectedHeight);
        String actSize = Integer.toString(actualImage.getWidth()) + "x" + Integer.toString(actualImage.getHeight());
        assertTrue("The size of the image " + expectedImage + " and " + actual + " must match. Expected: " + expSize + ", Actual: " + actSize, expSize.equals(actSize));

        // 2. check each pixel
        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                List<Integer> okPixels = new ArrayList<Integer>();
                okPixels.add(expectedImage.getRGB(x, y));
                assertTrue("The image " + actual + " don't match in the pixel (" + x + "/" + y + ") with any expected image", okPixels.contains(actualImage.getRGB(x, y)));
            }
        }
    }
}
