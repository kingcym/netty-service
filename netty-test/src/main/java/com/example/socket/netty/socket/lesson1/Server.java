package com.example.socket.netty.socket.lesson1;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/9 23:22
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        log.info("服务器准备就绪。。。");

        //等待客户端连接
        for (;;){
            //阻塞，直到有客户端连接进来
            Socket client = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.start();
        }

    }


    private static class ClientHandler extends Thread{
        private final Socket socket;
        private boolean flag = true;
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                /**
                 * 1.BufferedReader:
                 *      从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。 可以指定缓冲区的大小，或者可使用默认的大小。大多数情况下，默认值足够大。
                 * 2.InputStreamReader : 一次读取一个字符
                 *      是字节流通向字符流的桥梁,封裝了InputStream在里头, 它以较高级的方式,一次读取一个一个字符，以文本格式输入 / 输出，可以指定编码格式；
                 * 3.InputStream : 一次读取一个字节
                 */
                log.info("新客户端连接：地址：{}，端口：{}",socket.getInetAddress(),socket.getPort());
                //得到输出流，变成对应 打印流
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                //得到输入流
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                do {
                    //拿到一条数据 (socket阻塞，直到有数据可读)
                    String readLine = bufferedReader.readLine();
                    if ("bye".equalsIgnoreCase(readLine)){
                        flag = false;
                        //回送
                        printStream.println("bye");
                    } else {
                        log.info("从客户端读取到数据：{}",readLine);
                        printStream.println("回送：" + readLine.length());
                    }
                } while (flag);
                printStream.close();
                bufferedReader.close();
            } catch (Exception e){
                log.error("新客户操作异常：",e);
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    log.error("新客户关闭异常：",e);
                }
            }
            log.info("客户端退出");
        }
    }
}
