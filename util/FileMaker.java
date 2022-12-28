package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMaker {
    public static void makeFile(String filePath) {
        File file = new File(filePath);
        try {
            boolean newFile = file.createNewFile();
            if(newFile) {
                System.out.println("Created new file \" " +filePath+"\".");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void write(String filePath, InputStream inputStream) {
        makeFile(filePath);
        try (DataOutputStream contend=new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath))))) {
            byte[] data=new byte[1024*1024];
            int len=inputStream.read(data);
            if(len!=-1) {
                contend.write(data,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
