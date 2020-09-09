/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author joseluischavez
 */
public class FileUtils {

    public static void createPath(String generalPath, String subPath) {
        String[] paths = subPath.split("/");
        String toCreate = generalPath;

        for (String path : paths) {
            toCreate = toCreate.concat("/").concat(path);
            File file = new File(toCreate);
            file.mkdir();
        }
    }

    public static boolean createFile(File file) throws IOException {
        return file.createNewFile();
    }

    public static boolean writeFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            createFile(file);

            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);
            myWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
