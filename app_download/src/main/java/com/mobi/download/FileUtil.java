package com.mobi.download;

import java.net.URL;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 18:00
 * Version: 1.0
 * Description:
 */
class FileUtil {
    //获取下载文件的名称
    public static String getFileName(URL url) {
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/") + 1);
    }
}
