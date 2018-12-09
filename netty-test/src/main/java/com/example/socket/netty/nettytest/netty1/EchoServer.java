package com.example.socket.netty.nettytest.netty1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/12/2 14:23
 */
@Slf4j
public class EchoServer {
    public static void main(String[] args) {
        new EchoServer().start();
    }

    private void start() {
        //相当轮询seletor的线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理逻辑线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            final EchoServerHandler handler = new EchoServerHandler();
            //创建bootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    //指定NIO传输的channel
                    .channel(NioServerSocketChannel.class)
                    //给每一个连接设置基本的tcp连接属性
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    //设置客户端连接基本属性
                    .childAttr(AttributeKey.newInstance("childAttr"),"childAttrValue")
                    .localAddress(new InetSocketAddress(9090))
                    .handler(new ServerHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            log.info("SocketChannel加入：{}",ch.remoteAddress());
                            ch.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            log.info("绑定成功");
            future.channel().closeFuture().sync();
            log.info("通道关闭");
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
