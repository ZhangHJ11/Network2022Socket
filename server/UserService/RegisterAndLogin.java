package server.UserService;

import server.HTTPServer;
import server.Request;
import server.returnValue;

import java.util.Arrays;
import java.util.HashMap;

public class RegisterAndLogin {
    private HashMap<String, String> clientMessages;
    // 单例模式
    private static final RegisterAndLogin now = new RegisterAndLogin();
    static private final String LOGIN_SUCCESS_DIR = "/loginSuccess.html";
    static private final String LOGIN_FAIL_DIR = "/loginFail.html";
    static private final String REGISTER_SUCCESS_DIR = "/registerSuccess.html";
    static private final String REGISTER_FAIL_DIR = "/registerFail.html";
    public Request request;
    public static int statusCode;
    public static String location;

    public RegisterAndLogin() {
        clientMessages = new HashMap<>();
    }

    public static RegisterAndLogin getClientList() {
        return now;
    }

    public void deal(String type, String name, String password) {
        if ("register".equals(type)) {
            if(!register(name, password)) {
                location = REGISTER_FAIL_DIR;
                statusCode = 200;
            }
            else {
                location =  REGISTER_SUCCESS_DIR;
                statusCode = 200;
            }
        } else if ("login".equals(type)) {
            boolean registerRet = login(name, password);
            if (!registerRet){
                location = LOGIN_FAIL_DIR;
            }
            else {
                location = LOGIN_SUCCESS_DIR;
            }
            statusCode = 200;
        }
    }

    private boolean register(String username, String password) {
        if (clientMessages.containsKey(username)) return false;
        clientMessages.put(username, password);
        return true;
    }

    private boolean login(String username, String password) {
        if (!clientMessages.containsKey(username)) return false;
        return clientMessages.get(username).equals(password);
    }

}
