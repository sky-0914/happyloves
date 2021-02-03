package cn.happyloves.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author zc
 * @date 2021/2/2 18:28
 */
@ChannelHandler.Sharable// 为了线程安全
public class ClientChannelHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 当通道就绪就会触发
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client: " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发
     *
     * @param channelHandlerContext 上下文对象
     * @param o                     传输消息体
     * @throws Exception 异常信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf = (ByteBuf) o;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址：" + channelHandlerContext.channel().remoteAddress());
    }

    /**
     * 异常信息
     *
     * @param ctx   上下文对象
     * @param cause 异常
     * @throws Exception 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
