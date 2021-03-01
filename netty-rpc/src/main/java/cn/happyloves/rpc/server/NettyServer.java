package cn.happyloves.rpc.server;

import cn.happyloves.rpc.message.RpcMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Netty服务端
 *
 * @author zc
 * @date 2021/2/24 13:23
 */
@Slf4j
public class NettyServer {

    private Channel channel;

    public void start(int port) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new SimpleChannelInboundHandler<RpcMessage>() {

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            System.out.println("新连接：===>>> " + ctx.channel().remoteAddress());
                                            super.channelActive(ctx);
                                        }

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {

                                        }


                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            ctx.close();
                                        }
                                    });
                        }
                    });

            final ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
            channel = channelFuture.channel();
            channel.closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void stop() {
        channel.closeFuture().syncUninterruptibly();
    }

    static class MethodHandle {

        @Autowired
        private AnnotationConfigApplicationContext context;

        public Object invoking(RpcMessage rpcMessage) {
            final Object bean = context.getBean(rpcMessage.getName());


            return null;
        }
    }
}
