package Test;

import Client.NewClient;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VersatileTest {

    @Test
    public void test1() throws IOException {
        String input="Y\na\na\nget\n./Resources/index.html\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }

    @Test
    public void test2() throws IOException {
        String input="a\na\nget\n./Resources/a.txt\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }

    @Test
    public void test3() throws IOException {
        String input="a\nb\nget\n./Resources/301origin.html\nstop\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",true);
    }

    @Test
    public void test4() throws IOException {
        String input="a\nb\nget\n./Resources/4.jpg\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }

    @Test
    public void test5() throws IOException {
        String input="a\nb\npost ./Resources/index.html\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }
}
