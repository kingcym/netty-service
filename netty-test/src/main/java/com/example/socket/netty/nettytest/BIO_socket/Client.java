package com.example.socket.netty.nettytest.BIO_socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author 闪电侠
 */
@Slf4j
public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9080;
    private static final int SLEEP_TIME = 5000;
    private static final int MAX_DATA_LEN = 1024;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端启动成功!");
                while (true) {
                    try {
                        String message = "hello world";
                        log.info("客户端发送数据: " + message);
                        socket.getOutputStream().write(message.getBytes());
                    } catch (Exception e) {
                        log.error("客户端发送数据异常：",e);
                    }
                    sleep();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = socket.getInputStream();
                    while (true) {
                        try {
                            byte[] data = new byte[MAX_DATA_LEN];
                            int len;
                            while ((len = inputStream.read(data)) != -1) {
                                String message = new String(data, 0, len);
                                log.info("收到服务端信息: " + message);
                            }
                        } catch (Exception e) {
                            log.error("收到服务端信息异常：",e);
                        }
                        sleep();
                    }
                } catch (IOException e) {
                    log.error("收到服务端异常：",e);
                }
            }
        }).start();

    }

    private static void sleep() {
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
