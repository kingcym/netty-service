package com.example.socket.netty.socket.lesson4_UDP_TCP.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) {
        ServerInfo info = ClientSearcher.searchServer(10000);
        log.info("获取到服务端地址 Server:" + info);
    }
}
