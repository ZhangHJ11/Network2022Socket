package Client.Requestmessage;

import lombok.Getter;
import util.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//首部行 用来说明浏览器、服务器和报文主题的一些信息
public class RequestHead {
    @Getter
    private HashMap<String, String> hashMap = new LinkedHashMap<>();

    public RequestHead() {

    }

    //    从输入流中构成报文头
    public RequestHead(InputStream inputStream) throws IOException {
        List<String> headline = new ArrayList<>();
        String tmp;
        while (!(tmp = InputStreamReader.readline(inputStream)).equals("")) {
            headline.add(tmp);
        }

        for (String head : headline) {
            head = head.trim();
            int index = head.indexOf(":");
            String name = head.substring(0, index);
            String value = head.substring(index + 1).trim();
            hashMap.put(name, value);
        }
    }

    public void put(String name, String value) {
        hashMap.put(name, value);
    }
}
