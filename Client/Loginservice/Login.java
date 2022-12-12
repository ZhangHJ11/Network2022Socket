package Client.Loginservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login {

    public String set() throws IOException{
        String username, password;
        System.out.println("====>>>> START LINE <<<<===");
        System.out.println("You are trying to connect to a socket server.");
        System.out.println("You will be registered if you try to log in for time first time.");
        System.out.println();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Your username:");
        username = bufferedReader.readLine();
        System.out.println("Your password:");
        password = bufferedReader.readLine();
        System.out.println();
        return "&username"+username+"&password"+password+System.lineSeparator();
    }

}
