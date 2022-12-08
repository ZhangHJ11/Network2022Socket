package server;

import com.sun.corba.se.spi.activation.Server;
import server.redirectList.RedirectList;
import server.UserService.RegisterAndLogin;
import server.redirectList.RedirectList;
import static server.HTTPServer.*;

import java.io.File;
import java.io.FileNotFoundException;
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
    private FileTable getFile = new FileTable();
    private static RedirectList redirectList = RedirectList.getRedirectList();
    private byte[] data = new byte[1024 * 1024]; // 1MB
    private int GETlen;
    private String requestInfo;
    public String method;
    private String url;
    private String queryStr; // For POST, get query in Body
    public Map<String, List<String>> paraMap;
    private final String CRLF = "\r\n";
    public int statusCode;
    public String location;
    public byte[] fileData;

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return url;
    }

    public Request(Socket client) {
        // redirectList = RedirectList.getRedirectList();
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
        handle();
    }

    /**
     * 请求行的数据
     */
    private void parseGETInfo() {
        // System.out.println(requestInfo);
        method = requestInfo.substring(0, requestInfo.indexOf("/"));
        method = method.substring(0, 3);
        System.out.println(method);
        int tmp1 = requestInfo.indexOf("/") + 1;
        int tmp2 = requestInfo.indexOf("HTTP/") - 1;
        url = requestInfo.substring(tmp1, tmp2);
        // 版本默认 HTTP/1.1 不做处理
        // post 才有实体主体
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

    public boolean isKeepAlive() {
        if ((requestInfo.indexOf("Keep-Alive") != -1)) {
            return true;
        }
        return false;
    }

    /**
     * 通过首部字段号查询之后的值
     * 
     * @param key
     * @return
     */
    public String[] getParaValues(String key) {
        List<String> list = paraMap.get(key);
        if (list == null || list.size() == 1) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    public byte[] getFileData(String location) {
        byte[] FileData = new byte[0];
        try {
            FileData = FileHandle.readFromFile(System.getProperty("user.dir") + File.separator + location);
        } catch (FileNotFoundException ex) {
            System.out.println("未找到文件");
            return null;
        }
        return FileData;
    }

    public void handle() {
        if (method.equals("GET")) {
            System.out.println(url);
            String redirectQuery = redirectList.search(url);
            System.out.println(redirectQuery);
            if (!redirectQuery.equals("")) {
                // 301,302
                statusCode = Integer.parseInt(redirectQuery.substring(0, 3));
                location = BIND_DIR + redirectQuery.substring(3);
            } else {
                statusCode = 200;
                location = BIND_DIR + url;
                // 304
                // 文件修改时间
                Long getTime = getFile.getModifiedTime(location);
                Long modifyTime = 0L/* modifiedFileTable.getModifiedTime(location) */;
                if (getTime >= modifyTime) {
                    statusCode = 304;
                    location = BIND_DIR + NOT_MODIFIED_RES;
                } else {
                    // 修改文件
                    getFile.modify(location);
                }
            }
            // System.out.println(statusCode);
            // System.out.println(location);
            fileData = getFileData(location);
            if (fileData == null) {
                statusCode = 404;
                location = BIND_DIR + NOT_FOUND_RES;
                fileData = getFileData(location);
            }

        } else if (method.equals("POST")) {
            if (getParaValues("type") != null) {
                returnValue value = RegisterAndLogin.getClientList().deal(getParaValues("type")[0],
                        getParaValues("name")[0], getParaValues("password")[0]);
                location = value.location;
                statusCode = value.statusCode;
                fileData = value.FileData;
                return;
            }
            location = BIND_DIR + url;
        } else {
            // 405
            statusCode = 405;
            location = BIND_DIR + METHOD_NOT_ALLOWED_RES;
            fileData = getFileData(location);
        }
    }
}
