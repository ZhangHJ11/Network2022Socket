package Client.Requestmessage;



//    请求行
public class RequestLine {

    private String method=null;

    private String url=null;

    private String version=null;

    public RequestLine(String Method, String Url) {
        this.method = Method;
        this.url = Url;
        this.version = "HTTP/1.1";
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
