package Client.Requestmessage;

//    请求行
public class Requestline {

    public String method;
    public String url;
    public String version;

    public Requestline(String Method, String Url) {
        this.method = Method;
        this.url = Url;
        this.version = "HTTP/1.1";
    }
}
