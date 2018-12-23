package com.example.socket.netty.socket.lesson4_UDP_TCP.server;


import com.example.socket.netty.socket.lesson4_UDP_TCP.constants.UDPConstants;
import com.example.socket.netty.socket.lesson4_UDP_TCP.utils.ByteUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.UUID;

@Slf4j
class ServerProvider {
    private static Provider PROVIDER_INSTANCE;

    static void start(int port) {
        stop();
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn, port);
        provider.start();
        PROVIDER_INSTANCE = provider;
    }

    static void stop() {
        if (PROVIDER_INSTANCE != null) {
            PROVIDER_INSTANCE.exit();
            PROVIDER_INSTANCE = null;
        }
    }

    private static class Provider extends Thread {
        private final byte[] sn;
        private final int port;
        private boolean done = false;
        private DatagramSocket ds = null;
        // 存储消息的Buffer
        final byte[] buffer = new byte[256];

        Provider(String sn, int port) {
            this.sn = sn.getBytes();
            this.port = port;
        }

        @Override
        public void run() {
            log.info("UDPProvider Started...");
            try {
                // 监听本地30201 端口
                ds = new DatagramSocket(UDPConstants.PORT_SERVER);
                // 接收消息的Packet
                DatagramPacket receivePack = new DatagramPacket(buffer, buffer.length);
                while (!done) {
                    // 接收
                    ds.receive(receivePack);
                    // 打印接收到的信息与发送者的信息
                    // 发送者的IP地址
                    String clientIp = receivePack.getAddress().getHostAddress();
                    int clientPort = receivePack.getPort();
                    int clientDataLen = receivePack.getLength();
                    byte[] clientData = receivePack.getData();
                    //固定头 + cmd（short） + 端口 （int）
                    boolean isValid = clientDataLen >= (UDPConstants.HEADER.length + 2 + 4)
                            && ByteUtils.startsWith(clientData, UDPConstants.HEADER);
                    log.info("服务端收到来自{}：{}客户端信息,是否有效：{}", clientIp ,clientPort , isValid);

                    if (!isValid) {
                        // 无效继续
                        continue;
                    }
                    // 解析命令与回送端口
                    int index = UDPConstants.HEADER.length;
                    //字节变short
                    short cmd = (short) ((clientData[index++] << 8) | (clientData[index++] & 0xff));
                    //字节变int
                    //todo 如何变化
                    int responsePort = (
                            ((clientData[index++]) << 24) |
                            ((clientData[index++] & 0xff) << 16) |
                            ((clientData[index++] & 0xff) << 8) |
                            ((clientData[index] & 0xff))
                    );

                    // 判断合法性
                    if (cmd == 1 && responsePort > 0) {
                        // 构建一份回送数据
                        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                        byteBuffer.put(UDPConstants.HEADER);
                        byteBuffer.putShort((short) 2);
                        byteBuffer.putInt(port);
                        byteBuffer.put(sn);
                        int len = byteBuffer.position();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(buffer,
                                len,
                                receivePack.getAddress(),
                                responsePort);
                        ds.send(responsePacket);
                        log.info("服务端给{}：{}客户端回信息，sn值为：{}",clientIp,responsePort,sn);
                    } else {
                        log.info("服务端接收的指令id不支持,cmd: {}" ,cmd);
                    }
                }
            } catch (Exception ignored) {
            } finally {
                close();
            }
            // 完成
            log.info("UDPProvider Finished...");
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
