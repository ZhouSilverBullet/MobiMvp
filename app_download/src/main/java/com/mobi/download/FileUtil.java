package com.mobi.download;

import android.text.TextUtils;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 18:00
 * Version: 1.0
 * Description:
 */
class FileUtil {
    //获取下载文件的名称
    public static String getFileName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "1.apk";
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
