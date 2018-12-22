/**
 * Copyright (C),2018, 萤石
 * FileName: UdpReceive
 * Author:   caiyaming
 * Date:     2018-12-11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.socket.netty.socket.lesson2_UDP;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @description：<br>
 *
 * @author: caiyaming
 * @date: 2018-12-11 19:21
 * @since: V1.0.0
 */
@Slf4j
public class UDPSearcher {
    public static void main(String[] args)throws Exception {
        //作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        byte[] requestDatas = ("hello word").getBytes();
        //根据发生着构造一份回送消息
        DatagramPacket requsetPack = new DatagramPacket(requestDatas,
                requestDatas.length);
        requsetPack.setAddress(InetAddress.getLocalHost());
        requsetPack.setPort(20000);
        ds.send(requsetPack);


        //构建接收实体
        byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf,buf.length);
        //3，通过scoket服务的receive方法接收数据，
        ds.receive(receivePack);//阻塞式方法
        //打印接收到信息和发送者信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int length = receivePack.getLength();
        String message = new String(receivePack.getData(),0,length);
        log.info("UDPSearcher收到 {}:{}的信息,{}",ip,port,message);


        //4,关闭资源
        ds.close();
    }
}
