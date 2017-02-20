package soselab.mpg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by bernie on 2017/2/20.
 */
public class Util {
    public static String getResourceContent(String path) {
        try {
            String file0 = Util.class.getResource(path).getFile();
            byte[] bytes = Files.readAllBytes(Paths.get(file0));
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
