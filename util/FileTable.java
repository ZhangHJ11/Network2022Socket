package util;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于记录文件的修改时间，304操作会用到
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
     */
    public void initInAFolder(String path) {
        lock.lock();
        File file = new File(path);
        File[] tmpList = file.listFiles();
        assert tmpList != null;
        for (File value : tmpList) {
            if (value.isFile()) {
                String filePath = value.toString();
                filePath = filePath.replace((char) 92, '/');
                filePath = filePath.substring(9);
                files.put(filePath, System.currentTimeMillis());
            }
        }
        lock.unlock();
    }
    /**
     * 修改一个文件
     */
    public void modify(String file) {
        lock.lock();
        if (files.get(file) == null) {
            files.put(file, System.currentTimeMillis());
        } else {
            files.replace(file, System.currentTimeMillis());
        }
        lock.unlock();
    }

    /**
     * 获取一个文件的修改时间，没出现过则返回 -1
     */
    public Long getModifiedTime(String file) {
        Long ret = -1L;
        lock.lock();
        if (files.get(file) != null) ret = files.get(file);
        lock.unlock();
        return ret;
    }
}
