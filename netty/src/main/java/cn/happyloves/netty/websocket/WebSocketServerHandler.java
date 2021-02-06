package cn.happyloves.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * websocket
 *
 * @author zc
 * @date 2021/2/6 11:38
 * websocket的消息体是TextWebSocketFrame
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 全局管理Channel,必须加static不然就不是单例了
     */
    static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(new TextWebSocketFrame("客户端：" + ctx.channel().remoteAddress() + "加入了~~~"));
        channelGroup.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
        channelGroup.writeAndFlush(new TextWebSocketFrame("客户端：" + ctx.channel().remoteAddress() + "离开了！！！"));
        ctx.channel().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        final Channel channel = channelHandlerContext.channel();
        //正常的TEXT消息类型
        log.info("客户端[{}],发送消息: {}", channel.remoteAddress(), msg.text());
        channelGroup.forEach(channel1 -> {
            if (channel != channel1) {
                channel1.writeAndFlush(new TextWebSocketFrame("客户端：" + channel.remoteAddress() + "说：" + msg.text()));
            } else {
                channel1.writeAndFlush(new TextWebSocketFrame("我：" + channel.remoteAddress() + "说：" + msg.text()));
            }
        });
    }

}
