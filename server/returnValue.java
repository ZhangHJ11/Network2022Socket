package server;

public class returnValue {
    public int statusCode;
    public String location;

    public byte[] FileData;
    public returnValue() {
        statusCode = 0;
        location = null;
        FileData = null;
    }

    public returnValue(int statusCode, String location,byte[] FileData) {
        this.statusCode = statusCode;
        this.location = location;
        this.FileData = FileData;
    }
}
