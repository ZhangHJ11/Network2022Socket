package Client;

import Client.methods.Get;
import Client.methods.Post;
import Client.methods.RequestMethod;

public class NewClient {
    private String host;
    private int port;
    private static Connections pool=new Connections();
    RequestMethod requestMethod;


    public NewClient(int port,String host,String method){
        this.port=port;
        this.host=host;
        if(method.equals("Get")){
            this.requestMethod=new Get(host,port,pool);
        }
        else if(method.equals("Post")){
            this.requestMethod=new Post(host,port,pool);
        }
    }

    public void switchMode(String method){
        if(method.equals("GET")){
            this.requestMethod=new Get(host,port,pool);
        }
        else if(method.equals("POST")) {
            this.requestMethod=new Post(host,port,pool);
        }
    }
}
