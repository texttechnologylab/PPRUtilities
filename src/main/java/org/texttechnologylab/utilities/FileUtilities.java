package org.texttechnologylab.utilities;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * Auxiliary class used for reading and writing files
 * @author Giuseppe Abrami
 */
public class FileUtilities {

    /**
     * Read content from a file
     * @param pFile
     * @return
     * @throws IOException
     */
    public static String readContent(File pFile) throws IOException {
        return Files.readString(pFile.toPath());
    }

    /**
     * Read content from a file
     * @param pFile
     * @return
     * @throws IOException
     */
    public static JSONArray readContentAsJSON(File pFile) throws IOException {
        return new JSONArray(readContent(pFile));
    }

    /**
     * Write content to file
     * @param pFile
     * @param sContent
     * @throws IOException
     */
    public static void writeContent(File pFile, String sContent) throws IOException {
        writeContent(pFile, sContent, Charset.forName("UTF-8"));
    }

    /**
     * Write content with specific character set as encoding, in a file
     * @param pFile
     * @param sContent
     * @param pCharset
     * @throws IOException
     */
    public static void writeContent(File pFile, String sContent, Charset pCharset) throws IOException {
        if(!pFile.toPath().getParent().toFile().exists()) {
            Files.createDirectories(pFile.toPath());
        }
        Files.write(pFile.toPath(), sContent.getBytes(pCharset));
    }
}
