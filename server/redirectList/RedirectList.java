package server.redirectList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 304的重定向列表
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
        redirectLists.put("/302origin.html", "/302dest.html");
        redirectType.put("/302origin.html", 302);
    }

    public String search(String originURL){
        if(!redirectLists.containsKey(originURL)){
            return "";
        }
        return /*redirectType.get(originURL) + */redirectLists.get(originURL);
    }

    /**
     * 重新读取配置文件
     * @param path
     */
    private void paraseConfig(String path) {
        String []line;
        File file = new File(path);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                line = tempString.split("\\s+");
                redirectLists.put(line[0], line[1]);
                redirectType.put(line[0], Integer.parseInt(line[2]));
            }
        } catch (IOException e) {
            System.out.println("配置文件读取失败");
        }
    }
}
