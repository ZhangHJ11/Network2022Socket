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

    public RegisterAndLogin() {
        clientMessages = new HashMap<>();
    }

    public static RegisterAndLogin getClientList() {
        return now;
    }

    public returnValue deal(String type, String name, String password) {
        returnValue answer = null;
        if ("register".equals(type)) {
            if (!register(name, password)) {
                String location = HTTPServer.BIND_DIR + REGISTER_FAIL_DIR;
                answer = new returnValue(200, location, Arrays.toString(request.getFileData(location)));
            }
            else {
                String location = HTTPServer.BIND_DIR + REGISTER_SUCCESS_DIR;
                answer = new returnValue(200,location,Arrays.toString(request.getFileData(location)));
            }
        } else if ("login".equals(type)) {
            boolean registerRet = login(name, password);
            String location;
            if (!registerRet){
                location = HTTPServer.BIND_DIR + LOGIN_FAIL_DIR;
            }
            else {
                location = HTTPServer.BIND_DIR + LOGIN_SUCCESS_DIR;
            }
            answer = new returnValue(200, location,Arrays.toString(request.getFileData(location)));
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
        return clientMessages.get(username).equals(password);
    }

}
