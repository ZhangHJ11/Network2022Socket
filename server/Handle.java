package server;

import server.UserService.RegisterAndLogin;
import server.redirectList.RedirectList;
import util.FileTable;
import util.GetFile;

import java.io.IOException;
import java.util.ArrayList;

import static server.HTTPServer.*;


public class Handle {
    Request request;
    public static boolean isR = false;
    private FileTable getFile = new FileTable();
    public String method ;
    public String url;
    public int statusCode;
    public String location;
    public RedirectList redirectList = RedirectList.getRedirectList();
    public static ArrayList<String> fileList;
    public Handle(Request request){
        this.request = request;
        this.method = request.getMethod();
        this.url = request.getURL();
        fileList = setFileList();
    }


    public void handle() {
        if (method.equals("GET")) {
            String redirectQuery = redirectList.search(url);
            if (!redirectQuery.equals("")) {
                // 301,302
                statusCode = Integer.parseInt(redirectQuery.substring(10, 13));
                location = redirectQuery;
                request.setUrl(location);
                isR = true;
                return;
            }
            else {
                statusCode = 200;
                location = url;
                // 304
                // 文件修改时间
                Long getTime = getFile.getModifiedTime(location);
                Long modifyTime = 0L/* modifiedFileTable.getModifiedTime(location) */;
                if (getTime >= modifyTime) {
                    statusCode = 304;
                    location = NOT_MODIFIED_RES;
                    request.setUrl(location);
                } else {
                    // 修改文件
                    getFile.modify(location);
                }
            }
            System.out.println("this is " + statusCode + " " + location);
            if(isR){
                isR = false;
                return;
            }
            if(!fileList.contains(location)){
                statusCode = 404;
                location = NOT_FOUND_RES;
                request.setUrl(location);
            }
        }
        else if (method.equals("POST")) {
            if (request.getParaValues("type") != null) {
                RegisterAndLogin.getClientList().deal(request.getParaValues("type")[0],
                        request.getParaValues("name")[0], request.getParaValues("password")[0]);
                statusCode = RegisterAndLogin.statusCode;
                return;
            }
            location = url;
        }
        else {
            // 405
            statusCode = 405;
            location = METHOD_NOT_ALLOWED_RES;
            request.setUrl(location);
        }
    }

    public static boolean ifFileIn(String url){
        return fileList.contains(url);
    }

    public boolean judge(String location){
        for(int i = 0;i < fileList.size();i++){
            System.out.println(fileList.get(i));
            if(fileList.get(i).equals(location)){
                return true;
            }
        }
        return false;
    }
    /**
     * 建立文件表
     */
    private ArrayList<String> setFileList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Resources/2.png");
        list.add("Resources/3.zip");
        list.add("Resources/4.png");
        list.add("Resources/301origin.html");
        list.add("Resources/301dest.html");
        list.add("Resources/302dest.html");
        list.add("Resources/302origin.html");
        list.add("Resources/304.html");
        list.add("Resources/404.html");
        list.add("Resources/405.html");
        list.add("Resources/500.html");
        list.add("Resources/index.html");
        list.add("Resources/lab6.png");
        list.add("Resources/loginSuccess.html");
        list.add("Resources/loginFail.html");
        list.add("Resources/pic.png");
        list.add("Resources/post.html");
        list.add("Resources/post_success.html");
        list.add("Resources/registerFail.html");
        list.add("Resources/registerSuccess.html");
        list.add("Resources/temp.txt");
        list.add("Resources/testForm");
        list.add("Resources/uploadSuccess.html");
        return list;
    }
}
