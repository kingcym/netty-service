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

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @description：<br>
 *
 * @author: caiyaming
 * @date: 2018-12-11 19:21
 * @since: V1.0.0
 */
public class UdpReceive {
    public static void main(String[] args)throws Exception
    {
        //1,建立udp服务,通过DatagramScoket,建立端点
        DatagramSocket ds = new DatagramSocket(8888);//必须指定接收端应用程序的端口,用来匹配发送端数据包来的端口号
        while(true)
        {
            //2,创建一个字节数据包用于存储接收过来的字节数据
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf,buf.length);

            //3，通过scoket服务的receive方法接收数据，
            ds.receive(dp);//阻塞式方法

            //4,将字节数据转换为字符串并输出
            String message = new String(dp.getData(),0,dp.getLength());
            String IP = dp.getAddress().getHostAddress();
            int port = dp.getPort();
            System.out.println("IP:"+IP+"::"+"port:"+port);
            System.out.println("message:"+message);
        }

        //5,关闭资源
        //ds.close();
    }
}
