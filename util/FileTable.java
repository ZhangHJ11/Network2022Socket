package util;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于记录文件的修改时间，304 和 一些修改操作会用到
 */
public class FileTable {
    HashMap<String, Long> files;
    Lock lock;

    public FileTable() {
        files = new HashMap<>();
        lock = new ReentrantLock();
    }

    /**
     * 从一个文件夹中读取全部文件，修改时间记为读取时间
     * @param path
     */
    public void initInAFolder(String path) {
        lock.lock();
        File file = new File(path);
        File[] tmpList = file.listFiles();
        for (int i = 0; i < tmpList.length; i++) {
            if (tmpList[i].isFile()) {
                File file1 = tmpList[i];
                String filePath = file1.toString();
                filePath = filePath.replace((char)92,'/');
                filePath = filePath.substring(9);
                //System.out.println(filePath);
                files.put(filePath, System.currentTimeMillis());
            }
        }
        lock.unlock();
    }
    /*public void initInAFolder(String location) {
        lock.lock();
        location = location.replace((char)92,'/');
        files.put(location, System.currentTimeMillis());
        lock.unlock();
    }*/

    /**
     * 修改一个文件
     * @param file
     */
    public void modify(String file) {
        lock.lock();
        //System.out.println(file);
        if (files.get(file) == null) {
            //System.out.println("null");
            files.put(file, System.currentTimeMillis());
        } else {
            //System.out.println("yes");
            files.replace(file, System.currentTimeMillis());
        }
        lock.unlock();
    }

    /**
     * 获取一个文件的修改时间，没出现过则返回 -1
     * @param file
     * @return
     */
    public Long getModifiedTime(String file) {
        Long ret = -1L;
        lock.lock();
        //file = "./server/" + file;
        if (files.get(file) != null) ret = files.get(file);
        lock.unlock();
        return ret;
    }
}
