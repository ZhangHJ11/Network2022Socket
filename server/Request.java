package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.StreamReader;

public class Request {
    private InputStream fromClient;

    private byte[] data = new byte[1024 * 1024]; // 1MB
    private int GETlen;
    private String requestInfo;
    public String method;
    private String url;
    private String queryStr; // get query in Body
    public Map<String, List<String>> paraMap; // store the query in body
    private final String CRLF = "\r\n";

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return url;
    }

    public void setUrl(String newUrl) {
        this.url = newUrl;
    }

    // read info from client
    public Request(Socket client) {
        try {
            fromClient = client.getInputStream();
            data = StreamReader.getBytes(fromClient);
            GETlen = data.length;
            requestInfo = new String(data, 0, GETlen > 1000 ? 1000 : GETlen);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("GET error.");
            return;
        }
        parseClientInfo();
    }

    private void parseClientInfo() {
        method = requestInfo.substring(0, requestInfo.indexOf(" "));
        int tmp1 = requestInfo.indexOf(" ") + 3;
        int tmp2 = requestInfo.indexOf("HTTP/") - 1;
        url = requestInfo.substring(tmp1, tmp2);
        // 版本默认 HTTP/1.1 不做处理
        // 专门处理登陆post
        if (method.equals("POST")) {
            // linux 一个CRLF
            queryStr = requestInfo.substring(requestInfo.indexOf(CRLF + CRLF)).trim();
            paraMap = new HashMap<String, List<String>>();
            convertMap();
        }
        if (MIMETypes.getMIMELists().getMIMEType(url).equals("application/zip")) {
            System.out.println(requestInfo.substring(0, requestInfo.indexOf(CRLF + CRLF)));
        } else {
            System.out.println(requestInfo);
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

    public boolean isKeepAlive() {
        if ((requestInfo.indexOf("Keep-Alive") != -1)) {
            return true;
        }
        return false;
    }

    // 通过首部字段号查询之后的值
    public String[] getParaValues(String key) {
        List<String> list = paraMap.get(key);
        if (list == null) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    // get special field TimeOut
    public int getTimeOut() {
        int index1 = requestInfo.indexOf("Time:") + 6;
        int index2 = requestInfo.lastIndexOf(CRLF) - 1;
        return Integer.parseInt(requestInfo.substring(index1, index2));
    }

    // get special field Type
    public String getType() {
        int index1 = requestInfo.indexOf("Content-type:") + 14;
        int index2 = requestInfo.indexOf("Time:") - 1;
        return requestInfo.substring(index1, index2);
    }

    public byte[] getBody() {
        return queryStr.getBytes();
    }

}
