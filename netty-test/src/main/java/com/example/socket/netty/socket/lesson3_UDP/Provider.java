package com.example.socket.netty.socket.lesson3_UDP;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/17 23:40
 */
@Slf4j
public class Provider {
    //启动多个
    public static void main(String[] args) throws IOException {
        // 生成一份唯一标示
        String sn = UUID.randomUUID().toString();
        InnerProvider innerProvider = new InnerProvider(sn);
        innerProvider.start();

        // 读取任意键盘信息后可以退出
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        innerProvider.exit();
    }

    private static class InnerProvider extends Thread {
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        public InnerProvider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
           log.info("UDPProvider Started.");
            try {
                // 监听20000 端口
                ds = new DatagramSocket(20000,InetAddress.getByName("192.168.1.4"));

                while (!done) {

                    // 构建接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

                    // 接收
                    ds.receive(receivePack);

                    // 打印接收到的信息与发送者的信息
                    // 发送者的IP地址
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);
                    log.info("UDPProvider receive form ip:" + ip
                            + "port:" + port + "data:" + data);

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // 构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,
                                responseDataBytes.length,
                                receivePack.getAddress(),
                                responsePort);

                        ds.send(responsePacket);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }

            // 完成
            log.info("UDPProvider Finished.");
        }


        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }


        /**
         * 提供结束
         */
        void exit() {
            done = true;
            close();
        }

    }
}
