package Test;

import Client.NewClient;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VersatileTest {
    @Before
    public void init() {

    }

    // 第0个虚页放到第0个物理页框，然后加载到cache的第0、4、8、12行，tag均为全0
    @Test
    public void test1() throws IOException {
        NewClient client=new NewClient(8888,"127.0.0.1",false);
        String input="get ./Resources/index.html";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

}
