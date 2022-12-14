package Client.Loginservice;

import util.StreamReader;

import java.io.IOException;

public class Login {

    public String set() throws IOException{
        String username, password;
        System.out.println("====>>>> START LINE <<<<===");
        System.out.println("You are trying to connect to a socket server.");
        System.out.println("You will be registered if you try to log in for the first time.");
        System.out.println();
        System.out.println("Your username:");
        username = StreamReader.readline();
        System.out.println("Your password:");
        password = StreamReader.readline();
        System.out.println();
        return "&type=login"+"&username="+username+"&password="+password+System.lineSeparator();
    }

    public String change() throws IOException{
        String username, password;
        System.out.println("You are trying to change your account.Please input again");
        System.out.println();
        System.out.println("Your username:");
        username = StreamReader.readline();
        System.out.println("Your password:");
        password = StreamReader.readline();
        return "&type=login"+"&username="+username+"&password="+password+System.lineSeparator();
    }

}
