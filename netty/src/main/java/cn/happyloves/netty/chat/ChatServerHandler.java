package cn.happyloves.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZC
 * @date 2021/2/4 20:01
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组，管理所有的channel 全局ChannelGroup
    //GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("读取数据 ===>>> 客户端[{}]发送消息：{}", channelHandlerContext.channel().remoteAddress(), s);
        //这时，遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(channel -> {
            if (channel != channelHandlerContext.channel()) {
                channel.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息：" + s + "\n");
            } else {
                //把自己发送的消息发送给自己
                channel.writeAndFlush("[自己]发送了消息：" + s + "\n");
            }
        });
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel注册 ===>>> channelRegistered() -- 客户端绑定线程: ip=[{}]", ctx.channel().remoteAddress());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel移除 ===>>> channelUnregistered() -- 客户端解除线程绑定: ip=[{}]", ctx.channel().remoteAddress());
        super.channelUnregistered(ctx);
    }

    /**
     * 出于活动状态
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel活跃 ===>>> channelActive() -- 客户端准备就绪, ip=[{}]", ctx.channel().remoteAddress());
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "上线了~");
    }

    /**
     * 出于不活跃状态
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel不活跃，断开连接 ===>>> channelInactive() -- 客户端被关闭, ip=[{}]", ctx.channel().remoteAddress());
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "离线了~");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channel读完数据，读取完毕 ===>>> channelReadComplete() -- 本次读取客户端数据完毕: ip=[{}]", ctx.channel().remoteAddress());
        super.channelReadComplete(ctx);
    }

    /**
     * 触发事件
     *
     * @param ctx 上下文对象
     * @param evt 事件
     * @throws Exception 异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("channel用户事件触发 ===>>> userEventTriggered() -- 在规定时间内未进行读/写，链接断开");
        final Channel channel = ctx.channel();
        IdleStateEvent event = (IdleStateEvent) evt;
        switch (event.state()) {
            case READER_IDLE:
                log.warn("客户端[{}],没有读", channel.remoteAddress());
                break;
            case WRITER_IDLE:
                log.warn("客户端[{}],没有写", channel.remoteAddress());
                break;
            case ALL_IDLE:
                log.warn("客户端[{}],没有读写", channel.remoteAddress());
                break;
        }


    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel可写更改");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel异常捕获，关闭了 ===>>> exceptionCaught() -- 异常处理", cause);
        ctx.channel().close();
    }

    /**
     * 此方法表示连接建立，一旦建立连接，就第一个被执行
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("channel添加handler ===>>> handlerAdded() -- 增加逻辑处理器");
        Channel channel = ctx.channel();
        //该方法会将 channelGroup 中所有 channel 遍历，并发送消息，而不需要我们自己去遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天\n");
        //将当前的Channel加入到 ChannelGroup
        channelGroup.add(channel);
    }

    /**
     * 表示 channel 断开连接，最后一个执行
     *
     * @param ctx 上下文对象
     * @throws Exception 异常信息
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("channel删除handler ===>>> handlerRemoved() -- 逻辑处理器被移除");
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " " + sdf.format(new Date()) + "离开了\n");
        System.out.println("当前channelGroup大小 ：" + channelGroup.size());
    }

}
