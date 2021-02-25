package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jose Luis Ch.
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

    public static boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException ex) {
            System.err.println("El sistema no puede encontrar la ruta especificada");
            System.exit(0);
        }
        return false;
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
            //System.err.println("El sistema no puede encontrar la ruta especificada");
            return false;
        }
    }

}
