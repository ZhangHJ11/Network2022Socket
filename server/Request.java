package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private InputStream fromClient;
    private byte[] data = new byte[1024 * 1024]; // 1MB
    private int GETlen;
    private String requestInfo;
    private String method;
    private String url;
    private String queryStr; // For POST, get query in Body
    private Map<String, List<String>> paraMap; // �洢queryStr�л�ȡ��key-valueֵ������һ�Զ�
    private final String CRLF = "\r\n";

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return url;
    }

    public Request(Socket client) {
        try {
            fromClient = client.getInputStream();
            GETlen = fromClient.read(data);
            requestInfo = new String(data, 0, GETlen);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("GET error.");
            return;
        }
        parseGETInfo();
        if (method.equals("POST")) {
            paraMap = new HashMap<String, List<String>>();
            convertMap();
        }
    }

    private void parseGETInfo() {
        System.out.println(requestInfo);
        method = requestInfo.substring(0, requestInfo.indexOf("/"));
        int tmp1 = requestInfo.indexOf("/") + 1;
        int tmp2 = requestInfo.indexOf("HTTP/");
        url = requestInfo.substring(tmp1, tmp2);
        if (method.equals("POST")) {
            queryStr = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }
    }

    private void convertMap() {
        String[] queryS = queryStr.split("&");
        for (String query : queryS) {
            String[] keyAndValue = query.split("=");
            keyAndValue = Arrays.copyOf(keyAndValue, 2);
            if (!paraMap.containsKey(keyAndValue[0])) {
                paraMap.put(keyAndValue[0], new ArrayList<String>());
            }
            paraMap.get(keyAndValue[0]).add(keyAndValue[1]);
        }
    }

    public String[] getParaValues(String key) {
        List<String> list = paraMap.get(key);
        if (list == null || list.size() == 1) {
            return null;
        }
        return list.toArray(new String[0]);
    }
}