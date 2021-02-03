package cn.happyloves.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zc
 * @date 2021/2/2 15:21
 */
@Slf4j
@ChannelHandler.Sharable //线程安全
public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 读取客户端发送过来的消息
     *
     * @param channelHandlerContext 上下文对象，含有 管道pipeline，通道channel，地址等信息
     * @param o                     就是客户端发送的数据，默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server channelHandlerContext = " + channelHandlerContext);
        final Channel channel = channelHandlerContext.channel();
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();

        //将msg转成一个ByteBuf，比NIO的ByteBuffer性能更高
        ByteBuf buf = (ByteBuf) o;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //它是 write + flush，将数据写入到缓存buffer，并将buffer中的数据flush进通道
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~", CharsetUtil.UTF_8)).syncUninterruptibly();
    }

    /**
     * 处理异常，一般是关闭通道
     *
     * @param ctx   上下文对象
     * @param cause 异常
     * @throws Exception 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常信息
        cause.printStackTrace();
        ctx.close();
    }
}
