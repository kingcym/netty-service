package com.example.socket.netty.socket.lesson4_UDP_TCP.server;


import com.example.socket.netty.socket.lesson4_UDP_TCP.constants.TCPConstants;

import java.io.IOException;

/**
 * UDP与TCP联合使用，
 * 通过UDP发现局域网内服务端的地址和ip
 */
public class Server {
    public static void main(String[] args) {
        //开启服务端
        ServerProvider.start(TCPConstants.PORT_SERVER);
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerProvider.stop();
    }
}
