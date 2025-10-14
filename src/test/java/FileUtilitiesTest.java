import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.texttechnologylab.utilities.FileUtilities;

import java.io.File;
import java.io.IOException;


/**
 * Test-Class
 * @author Giuseppe Abrami
 */
@Nested
public class FileUtilitiesTest {

    /**
     * Determination of the system-related temp folder
     * @return
     */
    private static String getTempDir(){
        return System.getProperty("java.io.tmpdir");
    }

    @Test
    @DisplayName("WriteTest")
    public void testFileWrite() throws IOException {
        File tFile = new File(FileUtilitiesTest.getTempDir() + File.separator + "testFile.txt");
        FileUtilities.writeContent(tFile, new LoremIpsum().getParagraphs(1, 20));
    }

    @Test
    @DisplayName("ReadTest")
    public void testFileRead() throws IOException {
        File tFile = new File(FileUtilitiesTest.getTempDir() + File.separator + "testFile.txt");
        String sContent = FileUtilities.readContent(tFile);
        assert sContent != null && sContent.length() > 0;
    }


}
