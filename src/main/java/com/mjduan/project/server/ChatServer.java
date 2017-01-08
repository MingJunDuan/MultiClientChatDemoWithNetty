package com.mjduan.project.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Duan on 2017/1/8.
 */
public class ChatServer {
    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workderGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workderGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatServerInitializer());
        try {
            serverBootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workderGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        new ChatServer(8000).run();
    }

}
