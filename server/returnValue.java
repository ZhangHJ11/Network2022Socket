package server;

public class returnValue {
    public int statusCode;
    public String location;

    public String FileData;
    public returnValue() {
        statusCode = 0;
        location = null;
        FileData = null;
    }

    public returnValue(int statusCode, String location,String FileData) {
        this.statusCode = statusCode;
        this.location = location;
        this.FileData = FileData;
    }
}
