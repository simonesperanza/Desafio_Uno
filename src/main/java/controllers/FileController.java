package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class manage read/write
 * files operations
 */
public class FileController {

    /**
     * This method read the indicated file
     * from currrent directory
     * @param fileName file name to be read
     * @return json read from file
     */
    public String open(String fileName) {
        String json = "";
        try {
            String currentPath = System.getProperty("user.dir");
            String filePath = currentPath + "/" + fileName;
            json = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * @param fileName file name to be write
     * @param json json to be write in file
     * @return the path where file was saved
     */
    public String save(String fileName, String json) {
        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath+ "\\" + fileName;
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return filePath;
        }
    }
}
