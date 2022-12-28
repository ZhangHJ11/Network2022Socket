package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// read file
public class GetFile {
    public static byte[] getFile(String url) throws IOException {
        FileInputStream reader = new FileInputStream(
                System.getProperty("user.dir") + File.separator + "server" + File.separator + url);
        return StreamReader.getBytes(reader);
    }
}
