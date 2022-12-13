package Client;

import java.util.HashMap;

public class RedirectList {
    private static HashMap<String,String> redirectList;

    public static HashMap<String, String> getRedirectList() {
        return redirectList;
    }

    public void update(String from, String to) {
        redirectList.put(from,to);
    }
}
