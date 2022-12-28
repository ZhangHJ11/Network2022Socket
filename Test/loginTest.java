package Test;

import server.UserService.RegisterAndLogin;

public class loginTest {
    public static void main(String[] args){
        RegisterAndLogin.getClientList().deal("register","12345","1234");
        System.out.println(RegisterAndLogin.statusCode);
        System.out.println(RegisterAndLogin.location);
        RegisterAndLogin.getClientList().deal("login","12345","456");
        System.out.println(RegisterAndLogin.statusCode);
        System.out.println(RegisterAndLogin.location);
        RegisterAndLogin.getClientList().deal("login","12345","1234");
        System.out.println(RegisterAndLogin.statusCode);
        System.out.println(RegisterAndLogin.location);
    }
}
