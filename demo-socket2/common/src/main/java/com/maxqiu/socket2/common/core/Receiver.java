package com.maxqiu.socket2.common.core;

import java.io.Closeable;
import java.io.IOException;

public interface Receiver extends Closeable {
    boolean receiveAsync(IoArgs.IoArgsEventListener listener) throws IOException;
}
