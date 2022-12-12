package Client.Requestmessage;

//请求体
public class RequestBody {
    //Get method do not use this
    private String body ;
    public RequestBody(String body) { this.body = body; }
    public RequestBody() {}
    public String getBody(){
        return this.body;
    }
}
