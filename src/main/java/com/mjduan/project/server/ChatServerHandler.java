package com.mjduan.project.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * Created by Duan on 2017/1/8.
 */
public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {
    private static final ChannelGroup channels=new DefaultChannelGroup();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel inComingChannel = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[Server] - "+inComingChannel.remoteAddress()+" has joined\n");
        }
        channels.add(inComingChannel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel inComingChannel = ctx.channel();
        for (Channel channel : channels) {
            channel.write("[Server] - "+inComingChannel.remoteAddress()+" has left\n");
        }
        channels.remove(inComingChannel);
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.write("["+incoming.remoteAddress()+"]"+s+"\n");
            }
        }
    }
}
