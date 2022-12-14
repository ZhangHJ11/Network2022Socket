package util;

import java.io.*;

public class StreamReader {
    private static final BufferedReader bufferedReader=new BufferedReader(new java.io.InputStreamReader(System.in));
//    private static byte[] sysIn=new byte[1024];

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        byte[] t = new byte[1024*1024];
        int len=inputStream.read(t);
        byte[] nt=new byte[len];
        System.arraycopy(t,0,nt,0,len);
        return nt;
    }
    public static String readAll(InputStream inputStream) throws IOException {
        byte[] tmp=new byte[1024*1024];
        int len=inputStream.read(tmp);
        return new String(tmp,0,len);
    }
//    InputStream convert to String
    public static String readline(InputStream inputStream) throws IOException {
        byte[] tmp = new byte[100];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int char0;
        int NO = 0;
        while ((char) (char0 = bufferedInputStream.read()) != '\n') {
            tmp[NO++] = (byte) char0;
        }
        return new String(tmp, 0, NO);
    }
    public static String readline() throws IOException {
        byte[] tmp = new byte[1024];
        int char0;
        int NO = 0;
        while ((char) (char0 = bufferedReader.read()) != '\n') {
            tmp[NO++] = (byte) char0;
        }
        return new String(tmp, 0, NO);
    }
}
