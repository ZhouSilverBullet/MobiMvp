package com.mobi.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 15:19
 * @Dec 略
 */
public class VersionUpdateUtil {
    /**
     * 版本更新 格式化富文本
     *
     * @param str
     * @return
     */
    public static String[] splitPTag(String str) {
        String[] strResult;

        if (!TextUtils.isEmpty(str)) {
            strResult = str.split("<p>");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < strResult.length; i++) {
                if (i != 0) {
                    list.add(strResult[i].replace("</p>", ""));
                }
            }

            String[] result = new String[list.size()];
            result = list.toArray(result);
            return result;
        } else {
            return null;
        }
    }
}
