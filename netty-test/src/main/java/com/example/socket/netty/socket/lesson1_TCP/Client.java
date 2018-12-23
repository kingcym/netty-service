package com.example.socket.netty.socket.lesson1_TCP;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/9 18:18
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        //读 超时时间
        socket.setSoTimeout(3000);
        //连接服务器，连接超时时间3秒
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000),3000);
        log.info("已连接服务器。。。");
        log.info("客户端信息：地址：{}，端口：{}",socket.getLocalAddress(),socket.getLocalPort());
        log.info("服务端信息：地址：{}，端口：{}",socket.getInetAddress(),socket.getPort());
        try {
            todo(socket);
        } catch (Exception e) {
            log.error("客户端异常：",e);
        } finally {
            socket.close();
            log.info("客户端退出。。。。");
        }
    }

    private static void todo(Socket client) throws IOException {
        //输入
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        //获取Socket输出流
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        //获取Socket输入流
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        boolean flag = true;
        do {
            //发送到服务端
            socketPrintStream.println(input.readLine());
            //从服务器中读取一行
            String line = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(line)){
                flag = false;
            } else {
                log.info("从服务器读取到数据：{}",line);
            }
        } while (flag);

        socketPrintStream.close();
        socketBufferedReader.close();
    }
}
