package cn.happyloves.netty.http.get;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zc
 * @date 2021/2/3 10:31
 */
public class NettyHttpServer {

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        final ServerBootstrap bootstrapServer = new ServerBootstrap();

        bootstrapServer.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerChannelInitializer());

        final ChannelFuture channelFuture = bootstrapServer.bind(9999).syncUninterruptibly();
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
