package Client.Requestmessage;

import util.StreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RequestHead {

    /**
     * 发送报文的head部分，用hashmap来存储，把信息看成键值对
     */
    private HashMap<String, String> hashMap = new LinkedHashMap<>();

    public RequestHead() {

    }

    public void put(String name, String value) {
        hashMap.put(name, value);
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }
}
