package util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StreamConverter {
    public static InputStream convert(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
