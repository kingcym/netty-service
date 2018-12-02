package com.example.socket.netty.nettytest.BIO_socket;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
@Slf4j
public class ClientHandler {

    private static final int MAX_DATA_LEN = 1024;
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        log.info("接收客户端。。。。");
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
             inputStream = socket.getInputStream();
            while (true) {
                log.info("++++++++++++++");

                byte[] data = new byte[MAX_DATA_LEN];
                int len;
                // SocketInputStream 的read方法阻塞
                while ((len = inputStream.read(data)) != -1) {
                    String message = new String(data, 0, len);
                    log.info("客户端传来消息: " + message);
                    outputStream =socket.getOutputStream();
                    outputStream.write("已收到客户端数据".getBytes());
                }
                log.info("--------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
