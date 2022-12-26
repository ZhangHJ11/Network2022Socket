package server.redirectList;
import java.util.HashMap;

/**
 * 30X的重定向列表
 */

public class RedirectList {
    //只能有一个
    private static RedirectList redirectList = null;
    public static RedirectList getRedirectList(){
        if(RedirectList.redirectList == null){
            RedirectList.redirectList = new RedirectList();
        }
        return redirectList;
    }
    public static HashMap<String,String> redirectLists = new HashMap<>();
    public static HashMap<String,Integer> redirectType = new HashMap<>();
    private RedirectList(){
        redirectLists.put("Resources/301origin.html", "Resources/301dest.html");
        redirectType.put("Resources/301origin.html", 301);
        redirectLists.put("Resources/302origin.html", "Resources/302dest.html");
        redirectType.put("Resources/302origin.html", 302);
    }
    public String search(String originURL){
        if(!redirectLists.containsKey(originURL)){
            return "";
        }
        return redirectLists.get(originURL);
    }
}
