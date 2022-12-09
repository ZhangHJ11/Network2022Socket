package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetFile {
    public static String getFile(String url) throws IOException {
            BufferedReader reader = new BufferedReader(
                    new FileReader(System.getProperty("user.dir") + File.separator + "server" + File.separator + url));
            String tmp;
            StringBuilder ret = new StringBuilder();
            while ((tmp = reader.readLine()) != null) {
                ret.append(tmp);
            }
            reader.close();
            return ret.toString();
    }
}
