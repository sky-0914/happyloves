package cn.happyloves.rpc.client;

import cn.happyloves.rpc.message.RpcMessage;
import cn.happyloves.rpc.server.handle.ServerHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZC
 * @date 2021/3/1 23:30
 */
@Slf4j
public class NettyClientTest {
    
    private Channel channel;

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
//                                .addLast(new StringDecoder())
//                                .addLast(new StringEncoder())
                                .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                .addLast(new ObjectEncoder())
                                .addLast(new SimpleChannelInboundHandler<RpcMessage>() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                        for (int i = 0; i < 10; i++) {
//                                            RpcMessage rpcMessage = new RpcMessage();
//                                            rpcMessage.setName("cn.happyloves.rpc.api.Test1Api");
//                                            rpcMessage.setMethodName("testStr");
//                                            rpcMessage.setParTypes(new Class[]{int.class});
//                                            rpcMessage.setPars(new Object[]{1});
//                                            System.out.println(rpcMessage);
//                                            ctx.channel().writeAndFlush(rpcMessage);
//                                            System.out.println("================");
//                                        }
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
                                        System.out.println(rpcMessage);
                                    }
                                });
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).syncUninterruptibly();
        channel = channelFuture.channel();
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setName("cn.happyloves.rpc.api.Test1Api");
        rpcMessage.setMethodName("testStr");
        rpcMessage.setParTypes(new Class[]{int.class});
        rpcMessage.setPars(new Object[]{1});
        System.out.println(rpcMessage);
        channel.writeAndFlush(rpcMessage);
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

    public void stop() {
        channel.close();
    }

    public static void main(String[] args) {
        NettyClientTest nettyClient = new NettyClientTest();
        nettyClient.start(1111);
    }
}
