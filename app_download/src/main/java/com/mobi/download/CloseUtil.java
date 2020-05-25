package com.mobi.download;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/25 15:20
 * @Dec 略
 */
public class CloseUtil {
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
