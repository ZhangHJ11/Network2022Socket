package Test;

import Client.NewClient;
//import org.junit.Before;
//import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VersatileTest {
//    @Before
    public void init() {
    }


//    @Test
    public void test1() throws IOException {
        String input="get ./Resources/index.html";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }

//    @Test
    public void test2() throws IOException {
        String input="get ./Resources/a.txt";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",false);
    }

//    @Test
    public void test3() throws IOException {
        String input="get ./Resources/301origin.html\nstop ./Resources/index.html";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        NewClient client=new NewClient(8888,"127.0.0.1",true);
    }

}
