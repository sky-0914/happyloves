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
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端
 *
 * @author zc
 * @date 2021/2/24 13:23
 **/
@Slf4j
public class NettyServer {

    /**
     * server端处理器
     */
    private final ServerHandle serverHandle;
    /**
     * 服务端通道
     */
    private Channel channel;

    /**
     * 构造器
     *
     * @param serverHandle server处理器
     */
    public NettyServer(ServerHandle serverHandle) {
        this.serverHandle = serverHandle;
    }

    /**
     * 启动
     *
     * @param port 启动端口
     */
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
//                                    .addLast(new StringDecoder())
//                                    .addLast(new StringEncoder())
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

    /**
     * 关闭当前通道
     */
    public void stop() {
        channel.close();
    }
}
