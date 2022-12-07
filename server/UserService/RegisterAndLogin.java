package server.UserService;

import server.HTTPServer;
import server.returnValue;

import java.util.HashMap;

public class RegisterAndLogin {
    private HashMap<String, String> clientMessages;
    // 单例模式
    private static final RegisterAndLogin now = new RegisterAndLogin();
    static private String LOGIN_SUCCESS_DIR = "/loginSuccess.html";
    static private String LOGIN_FAIL_DIR = "/loginFail.html";
    static private String REGISTER_SUCCESS_DIR = "/registerSuccess.html";
    static private String REGISTER_FAIL_DIR = "/registerFail.html";

    public static RegisterAndLogin getClientList() {
        return now;
    }

    public returnValue deal(String type, String name, String password) {
        returnValue answer = null;
        if ("register".equals(type)) {
            if (!register(name, password))
                answer = new returnValue(200, HTTPServer.BIND_DIR + REGISTER_FAIL_DIR);
            else answer = new returnValue(200,HTTPServer.BIND_DIR + REGISTER_SUCCESS_DIR);
        } else if ("login".equals(type)) {
            boolean registerRet = login(name, password);
            if (!registerRet) answer = new returnValue(200, HTTPServer.BIND_DIR + LOGIN_FAIL_DIR);
            else answer = new returnValue(200, HTTPServer.BIND_DIR + LOGIN_SUCCESS_DIR);
        }
        return answer;
    }

    private boolean register(String username, String password) {
        if (clientMessages.containsKey(username)) return false;
        clientMessages.put(username, password);
        return true;
    }

    private boolean login(String username, String password) {
        if (!clientMessages.containsKey(username)) return false;
        if (!clientMessages.get(username).equals(password)) return false;
        return true;
    }

}
