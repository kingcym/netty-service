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
public class UdpSend {
    public static void main(String[] args)throws Exception {
        //1,建立udp服务,通过DatagramScoket,建立端点
        DatagramSocket ds = new DatagramSocket(10000);//可以指定发送端口,例如10000,也可以不用指定,让系统随机默认分配

        String s = "";
        //2,确定数据，并封装成数据包.DatagramPacket(byte[] buf, int length, InetAddress address, int port)
        for (int i =0 ;i<52000;i++){
            s = "好" +s;
        }

        byte[] buf = (s.getBytes());//直接指定信息数据

        System.out.println(buf.length);
        DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("127.0.0.1"),8888);

        //3，通过scoket服务的send方法发送数据，
        ds.send(dp);

        //4,关闭资源
        ds.close();
    }
}
