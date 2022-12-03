package Client.Requestmessage;


import lombok.Getter;

//    请求行
public class RequestLine {

    @Getter
    private String method=null;
    @Getter
    private String url=null;

    @Getter
    private String version=null;

    public RequestLine(String Method, String Url) {
        this.method = Method;
        this.url = Url;
        this.version = "HTTP/1.1";
    }
}
