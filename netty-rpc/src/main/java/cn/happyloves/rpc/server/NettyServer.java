package cn.happyloves.rpc.server;

import cn.happyloves.rpc.server.handle.ServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端
 *
 * @author zc
 * @date 2021/2/24 13:23
 **/
@Slf4j
public class NettyServer {

    private int port;
    private ServerHandle serverHandle;
    private Channel channel;

    public NettyServer(int port, ServerHandle serverHandle) {
        this.port = port;
        this.serverHandle = serverHandle;
    }

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
                                    .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(serverHandle);
                        }
                    });

            final ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
            log.info("服务端启动-端口: {}", port);
            channel = channelFuture.channel();
            channel.closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void stop() {
        channel.close();
    }
}
