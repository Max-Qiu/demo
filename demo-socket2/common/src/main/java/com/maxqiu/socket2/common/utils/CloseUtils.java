package com.maxqiu.socket2.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author max.qiu
 */
public class CloseUtils {
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
