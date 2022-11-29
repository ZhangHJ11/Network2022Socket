package LoginService;

import java.util.HashMap;
import java.util.Scanner;

public class ClientList {
    private HashMap<String, String> clientMessages;
    // 单例模式
    private static final ClientList now = new ClientList();

    public static ClientList getClientList() {
        return now;
    }

    public void init() {
        clientMessages = new HashMap<>();
        start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("注册（1）or登录（0）");
        int sign = scanner.nextInt();
        if (sign == 1) {
            register();
        } else {
            login();
        }
    }

    private void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        if (clientMessages.get(name).equals(password)) {
            System.out.println("登陆成功");
        } else {
            System.out.println("用户名或密码错误");
            login();
        }
    }

    private void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入您的用户名");
        String name = scanner.nextLine();
        System.out.println("请输入您的密码");
        String password = scanner.nextLine();
        clientMessages.put(name, password);
        System.out.println("注册成功");
        start();
    }
}
