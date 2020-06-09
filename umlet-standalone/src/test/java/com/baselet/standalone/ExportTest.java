package com.baselet.standalone;

import com.google.common.io.Files;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ExportTest {

    static String TEST_FILE_LOCATION;

    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();

    @BeforeClass
    public static void beforeClass() throws URISyntaxException {
        TEST_FILE_LOCATION = MainBatchmodeTest.class.getProtectionDomain().getCodeSource().getLocation().toURI().getSchemeSpecificPart() + MainBatchmodeTest.class.getCanonicalName().replace(".", "/").replace(MainBatchmodeTest.class.getSimpleName(), "");
    }

    @Test
    public void testExportToPng() throws Exception {
        // Export UXF to PNG
    }

    private File copyInputToTmp(String inputFilename) throws IOException {
        return copyToTmp(inputFilename);
    }

    private File copyToTmp(String file) throws IOException {
        File newFile = tmpDir.newFile();
        Files.copy(new File(TEST_FILE_LOCATION + file), newFile);
        return newFile;
    }
}
