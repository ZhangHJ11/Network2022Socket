package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class StreamConverter {
    public static OutputStream convert(String s) {
        int len=s.length();
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream(len);
        outputStream.write(s.getBytes(),0,len);
        return outputStream;
    }
}
