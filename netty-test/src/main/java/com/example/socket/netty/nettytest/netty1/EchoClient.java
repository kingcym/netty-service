package com.example.socket.netty.nettytest.netty1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/2 14:23
 */
@Slf4j
public class EchoClient {
    public static void main(String[] args) {
        new EchoClient().start();
    }

    private void start() {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            //创建bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    //指定NIO传输的channel
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1",9090))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.info("SocketChannel加入：{}",ch.remoteAddress());
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            log.info("连接成功");
            future.channel().closeFuture().sync();
            log.info("通道关闭");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                loopGroup.shutdownGracefully().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
