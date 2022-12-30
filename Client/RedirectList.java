package Client;

import java.util.HashMap;

public class RedirectList {
    //用于301永久重定向
    private static final HashMap<String, String> redirectList = new HashMap<>();

    public static HashMap<String, String> getRedirectList() {
        return redirectList;
    }

    public static void update(String from, String to) {
        redirectList.put(from, to);
    }
}
