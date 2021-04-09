package cn.happyloves.rpc.client.handle;

import cn.happyloves.rpc.message.RpcMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * @author zc
 * @date 2021/3/2 15:19
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientHandle extends SimpleChannelInboundHandler<RpcMessage> {
    /**
     * 定义消息Map，将连接通道Channel作为key，消息返回值作为value
     */
    private final ConcurrentMap<Channel, RpcMessage> rpcMessageConcurrentMap;

    public ClientHandle(ConcurrentMap<Channel, RpcMessage> rpcMessageConcurrentMap) {
        this.rpcMessageConcurrentMap = rpcMessageConcurrentMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        log.info("当前客户端地址：{}", channelHandlerContext.channel().remoteAddress());
//        for (int i = 0; i < 10; i++) {
//            RpcMessage rpcMessage = new RpcMessage();
//            rpcMessage.setName("cn.happyloves.rpc.api.Test1Api");
//            rpcMessage.setMethodName("testStr");
//            rpcMessage.setParTypes(new Class[]{int.class});
//            rpcMessage.setPars(new Object[]{1});
//            System.out.println(rpcMessage);
//            channelHandlerContext.channel().writeAndFlush(rpcMessage);
//            System.out.println("================");
//        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        log.info("客户端收到服务端消息：{}", rpcMessage);
        rpcMessageConcurrentMap.put(channelHandlerContext.channel(), rpcMessage);
    }
}
