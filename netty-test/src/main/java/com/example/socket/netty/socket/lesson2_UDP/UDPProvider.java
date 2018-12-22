/**
 * Copyright (C),2018, 萤石
 * FileName: UdpSend
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
 * @date: 2018-12-11 19:20
 * @since: V1.0.0
 */
@Slf4j
public class UDPProvider {
    /**
     * 1.接收
     * 2.回送
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception {
        //1,建立udp服务,通过DatagramScoket,建立端点
        DatagramSocket ds = new DatagramSocket(20000);//可以指定发送端口,例如10000,也可以不用指定,让系统随机默认分配
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
        log.info("Provider收到 {}:{}的信息,{}",ip,port,message);

        byte[] responseDatas = ("Provide回送：" + length).getBytes();
        //根据发生着构造一份回送消息
        DatagramPacket responsePack = new DatagramPacket(responseDatas,
                responseDatas.length,receivePack.getAddress(),receivePack.getPort());
        ds.send(responsePack);

        //4,关闭资源
        ds.close();
    }
}
