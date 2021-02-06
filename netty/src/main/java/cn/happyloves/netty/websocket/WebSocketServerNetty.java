package cn.happyloves.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2021/2/5 15:58
 */
public class WebSocketServerNetty {

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public WebSocketServerNetty() {
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();
    }

    public void start(int port) {
        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            final ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast(new IdleStateHandler(1, 1, 2, TimeUnit.MINUTES));
                            pipeline.addLast(new HttpServerCodec());
                            // 在进行大文件传输的时候，一次将文件的全部内容映射到内存中，很有可能导致内存溢出。
                            // 为了解决大文件传输过程中的内存溢出，Netty提供了ChunkedWriteHandler来解决大文件或者码流传输过程中可能发生的内存溢出问题。
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpObjectAggregator(1024 * 10));
                            pipeline.addLast(new WebSocketServerProtocolHandler("/happyloves"));
                            pipeline.addLast(new WebSocketServerHandler());

                        }
                    });
            final ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();

            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new WebSocketServerNetty().start(9999);
    }
}
