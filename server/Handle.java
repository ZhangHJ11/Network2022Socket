package server;

import server.UserService.RegisterAndLogin;
import server.redirectList.RedirectList;
import util.FileTable;
import util.GetFile;

import java.io.IOException;

import static server.HTTPServer.*;

public class Handle {
    Request request;
    private FileTable getFile = new FileTable();
    public String method ;
    public String url;
    public int statusCode;
    public RedirectList redirectList = RedirectList.getRedirectList();
    public Handle(Request request){
        this.request = request;
        this.method = request.getMethod();
        this.url = request.getURL();
    }
    public void handle() {
        String location;
        byte[] fileData;
        if (method.equals("GET")) {
            String redirectQuery = redirectList.search(url);
            if (!redirectQuery.equals("")) {
                // 301,302
                statusCode = Integer.parseInt(redirectQuery.substring(10, 13));
                location = redirectQuery.substring(0);
            } else {
                statusCode = 200;
                location = url;
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
            try {
                fileData = GetFile.getFile(location).getBytes();
            }catch (IOException e){
                e.printStackTrace();
                statusCode = 404;
                location = NOT_FOUND_RES;
            }
            /*if (fileData == null) {
                statusCode = 404;
                location = BIND_DIR + NOT_FOUND_RES;
                fileData = getFileData(location);
            }*/
        } else if (method.equals("POST")) {
            if (request.getParaValues("type") != null) {
                RegisterAndLogin.getClientList().deal(request.getParaValues("type")[0],
                        request.getParaValues("name")[0], request.getParaValues("password")[0]);
                statusCode = RegisterAndLogin.statusCode;
                return;
            }
            location = url;
        } else {
            // 405
            statusCode = 405;
            location = METHOD_NOT_ALLOWED_RES;
            try {
                fileData = GetFile.getFile(location).getBytes();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
