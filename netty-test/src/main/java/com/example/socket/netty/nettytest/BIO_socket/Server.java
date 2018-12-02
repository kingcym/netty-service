package com.example.socket.netty.nettytest.BIO_socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/1 17:43
 */
@Slf4j
public class Server {
    private static final int PORT = 9080;
    private ServerSocket serverSocket;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
            log.info("服务端启动成功，port: {}",PORT);
            start();
        } catch (Exception e) {
            log.error("serverSocket创建异常：",e);
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                System.out.println("服务端异常");
            }
        }
    }
}
