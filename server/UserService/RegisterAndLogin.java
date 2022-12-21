package server.UserService;

import java.util.HashMap;

public class RegisterAndLogin {
    private final HashMap<String, String> clientMessages;
    // 单例模式
    private static final RegisterAndLogin now = new RegisterAndLogin();
    static private final String LOGIN_SUCCESS_DIR = "Resources/loginSuccess.html";
    static private final String LOGIN_FAIL_DIR = "Resources/loginFail.html";
    static private final String REGISTER_SUCCESS_DIR = "Resources/registerSuccess.html";
    static private final String REGISTER_FAIL_DIR = "Resources/registerFail.html";
    static private final String RegisterAndLoginSuccess = "Resources/RegisterAndLoginSuccess.html";
    public static int statusCode;
    public static String location;

    private RegisterAndLogin() {
        clientMessages = new HashMap<>();
    }

    public static RegisterAndLogin getClientList() {
        return now;
    }

    public void deal(String type, String name, String password) {
        if ("register".equals(type)) {
            if(!register(name, password)) {
                location = REGISTER_FAIL_DIR;
            }
            else {
                location =  REGISTER_SUCCESS_DIR;
            }
            statusCode = 200;
        }
        else if ("login".equals(type)) {
            int registerRet = login(name, password);
            if (registerRet == 2){
                location = LOGIN_FAIL_DIR;
            }
            else if(registerRet == 1){
                location = LOGIN_SUCCESS_DIR;
            }
            else{
                location = RegisterAndLoginSuccess;
            }
            statusCode = 200;
        }
    }

    private boolean register(String username, String password) {
        if (clientMessages.containsKey(username)) return false;
        clientMessages.put(username, password);
        return true;
    }

    private int login(String username, String password) {
        //不包含这个username，新建一个
        if (!clientMessages.containsKey(username)){
            clientMessages.put(username,password);
            return 0;
        }
        //登录名和密码均正确
        if(clientMessages.get(username).equals(password)){
            return 1;
        }
        //密码错误
        return 2;
    }

}
