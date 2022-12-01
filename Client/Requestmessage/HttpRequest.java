package Client.Requestmessage;

public class HttpRequest {
    public Requestline requestline;
    public Requesthead requesthead;
    public Requestbody requestbody;

    public HttpRequest(Requestline requestline,Requesthead requesthead, Requestbody requestbody){
        this.requestline = requestline;
        this.requesthead = requesthead;
        this.requestbody = requestbody;
    }
}
