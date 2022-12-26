package server;

import server.UserService.RegisterAndLogin;
import server.redirectList.RedirectList;
import util.FileMaker;
import util.FileTable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static server.HTTPServer.*;

public class Handle {
    Request request;
    public static boolean isR = false;
    private boolean isDown = false;
    private static final FileTable getFile = new FileTable();
    public String method;
    public String url;
    public int statusCode;
    public String location;
    public RedirectList redirectList = RedirectList.getRedirectList();
    public static ArrayList<String> fileList;

    public Handle(Request request) {
        this.request = request;
        this.method = request.getMethod();
        this.url = request.getURL();
        fileList = setFileList();
    }

    public void setIsDown(){
        isDown = true;
    }
    public void handle() {
        //模拟服务器挂掉
        if(isDown){
            statusCode = 500;
            location = SERVER_ERROR_RES;
            request.setUrl(location);
        }
        if (method.equals("GET")) {
            String redirectQuery = redirectList.search(url);
            if (!redirectQuery.equals("")) {
                // 301,302
                statusCode = Integer.parseInt(redirectQuery.substring(10, 13));
                location = redirectQuery;
                request.setUrl(location);
                isR = true;
                return;
            } else {
                statusCode = 200;
                location = url;
                // 304
                // 文件修改时间
                Long getTime = getFile.getModifiedTime(location);
                Long modifyTime = modifiedFileTable.getModifiedTime(location);
                if (getTime > modifyTime) {
                    statusCode = 304;
                    location = NOT_MODIFIED_RES;
                    request.setUrl(location);
                } else {
                    // 修改文件
                    getFile.modify(location);
                }
            }
            //是否重定向
            if (isR) {
                isR = false;
                return;
            }
            //文件不存在
            if (!fileList.contains(location)) {
                statusCode = 404;
                location = NOT_FOUND_RES;
                request.setUrl(location);
            }
        } else if (method.equals("POST")) {
            //是否为注册登录
            if (request.getParaValues("type") != null) {
                RegisterAndLogin.getClientList().deal(request.getParaValues("type")[0], request.getParaValues("username")[0], request.getParaValues("password")[0]);
                statusCode = RegisterAndLogin.statusCode;
                location = RegisterAndLogin.location;
                request.setUrl(location);
            } else {
                InputStream data = new ByteArrayInputStream(request.getBody());
                String redirectQuery = redirectList.search(url);
                // 重定向
                if (!redirectQuery.equals("")) {
                    // 301,302
                    statusCode = Integer.parseInt(redirectQuery.substring(10, 13));
                    location = redirectQuery;
                    request.setUrl(location);
                    return;
                }
                //文件不存在，新建
                if (!fileList.contains(url)) {
                    statusCode = 200;
                    location = url;
                    FileMaker.makeFile("./server/" + url);
                    FileMaker.write("./server/" + url, data);
                    fileList.add(location);
                    modifiedFileTable.modify(location);
                }
                //文件存在
                else {
                    statusCode = 200;
                    location = url;
                    FileMaker.write("./server/" + url, data);
                    modifiedFileTable.modify(location);
                }
            }
        }
        //不支持的指令
        else {
            // 405
            statusCode = 405;
            location = METHOD_NOT_ALLOWED_RES;
            request.setUrl(location);
        }
    }

    /**
     * 建立文件表
     */
    private ArrayList<String> setFileList() {
        ArrayList<String> list = new ArrayList<>();
        File file = new File("./server/Resources");
        File[] files = file.listFiles();
        assert files != null;
        for(File file1 : files){
            list.add("Resources/" + file1.getName());
        }
        return list;
    }
}
