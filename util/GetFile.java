package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// read file
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

    public static String getFilecli(String url) throws IOException {
        // System.out.println(System.getProperty("user.dir") + File.separator + "server"
        // + File.separator + url);
        BufferedReader reader = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + File.separator + "Client" + File.separator + url));
        String tmp;
        StringBuilder ret = new StringBuilder();
        while ((tmp = reader.readLine()) != null) {
            ret.append(tmp);
        }
        reader.close();
        return ret.toString();
    }
}
