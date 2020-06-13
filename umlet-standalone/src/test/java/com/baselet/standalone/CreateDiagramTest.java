package com.baselet.standalone;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class CreateDiagramTest {

    static String TEST_FILE_LOCATION;

    @BeforeClass
    public static void beforeClass() throws URISyntaxException {
        TEST_FILE_LOCATION = MainBatchmodeTest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getSchemeSpecificPart() + MainBatchmodeTest.class.getCanonicalName().replace(".", "/").replace(MainBatchmodeTest.class.getSimpleName(), "");
        TEST_FILE_LOCATION += "our";
    }

    @Test
    public void testSaveDiagram() {
        String output = TEST_FILE_LOCATION + "/testSaveDiagram.uxf";

        MainStandalone.main(new String[] {
                "-action=generate",
                "-filename=testSaveDiagram",
                "-output=" + output
        });

        assertTrue(new File(output).exists());
    }
}
