package Test;

import org.junit.Before;
import org.junit.Test;
import util.FileMaker;
import util.StreamReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class FileMakerTest {
    String absolutePath="D:\\Files\\Learning\\2022Fall\\Internet based Computing\\socket\\code\\Network2022Socket\\Test\\";
    String relativePath="./Test/";
    String picturePath="D:\\Files\\Learning\\2022Fall\\Internet based Computing\\socket\\code\\Network2022Socket\\server\\Resources\\pic.png";
    String fileName=null;
    @Before
    public void init() {
    }

    @Test
    public void test1() {
        fileName="text.txt";
        FileMaker.makeFile(absolutePath+fileName);
    }

    @Test
    public void test2() {
        fileName="text.txt";
        FileMaker.makeFile(relativePath+fileName);
    }

    @Test
    public void test3() {
        fileName="text.txt";
        FileMaker.makeFile(absolutePath+fileName);
        String contend="Hello World!";
        InputStream inputStream=new ByteArrayInputStream(contend.getBytes());
        FileMaker.write(absolutePath+fileName,inputStream);
    }

    @Test
    public void test4() {
        fileName="text.txt";
        FileMaker.makeFile(relativePath+fileName);
        String contend="Hello World!";
        InputStream inputStream=new ByteArrayInputStream(contend.getBytes());
        FileMaker.write(relativePath+fileName,inputStream);
    }

    @Test
    public void test5() {
        File file=new File(picturePath);
        try (InputStream inputStream= Files.newInputStream(file.toPath())) {
            FileMaker.makeFile(relativePath+"pic.png");
            FileMaker.write(relativePath+"pic.png",inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void test6() {
        File file=new File(picturePath);
        try (InputStream inputStream= Files.newInputStream(file.toPath())) {

            byte[] t = new byte[1024*1024];
            int len=inputStream.read(t);
            byte[] nt=new byte[len];
            System.arraycopy(t,0,nt,0,len);
            InputStream inputStream1=new ByteArrayInputStream(nt);

            FileMaker.makeFile(relativePath+"pic.png");
            FileMaker.write(relativePath+"pic.png",inputStream1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
