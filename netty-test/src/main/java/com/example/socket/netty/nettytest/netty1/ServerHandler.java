package com.example.socket.netty.nettytest.netty1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        log.info("channel已经被注册到了EventLoop");
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channel处于活跃状态，可以接收发送数据");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel未连接到远程节点");
    }
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel已经被创建，但未注册到了EventLoop");
    }



    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("ChannelHandler添加到channelPipeline");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("channelPipeline移除hannelHandler");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端发生异常：",cause);
    }
}
