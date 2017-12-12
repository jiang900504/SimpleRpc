package com.jiang.rpc.core;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import com.jiang.rpc.model.MessageRequest;
import com.jiang.rpc.model.MessageResponse;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * @Description:Rpc客户端处理模块
 * @author hc
 *
 */
public class MessageSendHandler extends ChannelInboundHandlerAdapter {

	 private ConcurrentHashMap<String, MessageCallBack> mapCallBack = new ConcurrentHashMap<String, MessageCallBack>();

	    private volatile Channel channel;
	    private SocketAddress remoteAddr;

	    public Channel getChannel() {
	        return channel;
	    }

	    public SocketAddress getRemoteAddr() {
	        return remoteAddr;
	    }

	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        super.channelActive(ctx);
	        this.remoteAddr = this.channel.remoteAddress();
	    }

	    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
	        super.channelRegistered(ctx);
	        this.channel = ctx.channel();
	    }

	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        MessageResponse response = (MessageResponse) msg;
	        String messageId = response.getMessageId();
	        MessageCallBack callBack = mapCallBack.get(messageId);
	        if (callBack != null) {
	            mapCallBack.remove(messageId);
	            callBack.over(response);
	        }
	    }

	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        ctx.close();
	    }

	    public void close() {
	        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	    }

	    public MessageCallBack sendRequest(MessageRequest request) {
	        MessageCallBack callBack = new MessageCallBack(request);
	        mapCallBack.put(request.getMessageId(), callBack);
	        channel.writeAndFlush(request);
	        return callBack;
	    }
}
