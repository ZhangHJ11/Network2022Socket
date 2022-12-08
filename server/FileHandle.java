package server;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author Kevin
 * 用于读取，存储文件
 * @Description
 */
public class FileHandle {

    /**
     * @param data
     * @param path
     * @throws IOException
     * 存储二进制文件
     */
    public static void saveBinaryFile(byte[] data, String path) throws IOException {
        FileOutputStream fis = new FileOutputStream(path, false);
        BufferedOutputStream bos = null;
        bos = new BufferedOutputStream(fis);
        bos.write(data);
        bos.flush();
        bos.close();
    }

    public static void saveTextFile(String data,String path) throws IOException {
        if(path.endsWith("/"))path = path + "temp.txt";
        FileWriter writer=new FileWriter(path,false);
        writer.write(data);
        writer.flush();
        writer.close();
    }
    /**
     * @param FileLocation
     * @return
     * @throws FileNotFoundException 将FileLocation以字节形式读出
     */
    public static final byte[] readFromFile(String FileLocation) throws FileNotFoundException {
        InputStream in = null;
        in = new FileInputStream(FileLocation);
        byte[] data = getResAsStream(in);
        return data;
    }

    /**
     * 创建文件路径
     * @param MimeType
     * @param uri
     * @return
     */
    public static String createFilePath(String MimeType,String uri){
        String postFix= MIMETypes.getMIMELists().getReverseMIMEType(MimeType);
        String property = System.getProperty("user.dir");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String storage=new String(property+File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"client"+File.separator+"Resources"+File.separator+uri);
        return storage;
    }

    /**
     * 从资源输入流构建返回byte数组
     * @param in 输入流
     * @return byte数组流
     */
    public static  byte[] getResAsStream(InputStream in){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int len = 0; // 单次读取长度
        int totalLen = 0; // 所有内容长度
        byte[] bytes = new byte[2048];
        while(true)
        {
            try {
                if ((len = in.read(bytes)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            totalLen += len;
            try {
                os.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int dataLen = totalLen;
        byte [] ans=new byte[dataLen];
        ans = Arrays.copyOf(os.toByteArray(), dataLen);

        return ans;
    }
}
