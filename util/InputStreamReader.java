package util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReader {
//    InputStream convert to String
    public static String Readline(InputStream inputStream) throws IOException {
        byte[] tmp = new byte[100];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int char0;
        int NO = 0;
        while ((char) (char0 = bufferedInputStream.read()) != '\n') {
            tmp[NO++] = (byte) char0;
        }
        return new String(tmp, 0, NO);
    }
}
