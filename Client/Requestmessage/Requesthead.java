package Client.Requestmessage;

import util.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//首部行 用来说明浏览器、服务器和报文主题的一些信息
public class Requesthead {
    public HashMap<String, String> headmap = new LinkedHashMap<>();

    public Requesthead() {

    }

    //    从输入流中构成报文头
    public Requesthead(InputStream inputStream) throws IOException {
        List<String> headline = new ArrayList<>();
        String tmp;
        while (!(tmp = InputStreamReader.Readline(inputStream)).equals("")) {
            headline.add(tmp);
        }

        for (String head : headline) {
            head = head.trim();
            int index = head.indexOf(":");
            String name = head.substring(0, index);
            String value = head.substring(index + 1).trim();
            headmap.put(name, value);
        }
    }

    public void put(String name, String value) {
        headmap.put(name, value);
    }
}
