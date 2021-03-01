package cn.happyloves.rpc.client;

import cn.happyloves.rpc.message.RpcMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZC
 * @date 2021/3/1 23:30
 */
@Slf4j
public class NettyClient {

    public void start(int port) {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                .addLast(new ObjectEncoder())
                                .addLast(new SimpleChannelInboundHandler<RpcMessage>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
                                        System.out.println(rpcMessage);
                                    }
                                });
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).syncUninterruptibly();
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setName("testService");
        rpcMessage.setMethodName("test");
        rpcMessage.setParTypes(null);
        rpcMessage.setPars(null);
        System.out.println(rpcMessage);
        channelFuture.channel().writeAndFlush(rpcMessage);
        System.out.println("================");
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.start(1111);
    }
}
