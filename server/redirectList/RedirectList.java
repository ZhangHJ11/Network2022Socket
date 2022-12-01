package server.redirectList;

import java.util.HashMap;

/**
 * 304的重定向列表
 */

public class RedirectList {
    //只能有一个
    private static RedirectList redirectList = null;

    public static RedirectList getRedirectList(){
        return redirectList;
    }

    public static HashMap<String,String> redirectLists = new HashMap<>();
    public static HashMap<String,Integer> redirectType = new HashMap<>();

    private RedirectList(){
        redirectLists.put("/301origin.html", "/301dest.html");
        redirectType.put("/301origin.html", 301);
        redirectLists.put("/302origin.html", "/302dest.html");
        redirectType.put("/302origin.html", 302);
    }

    public String search(String originURL){
        if(!redirectLists.containsKey(originURL)){
            return "";
        }
        return redirectType.get(originURL) + redirectLists.get(originURL);
    }
}
