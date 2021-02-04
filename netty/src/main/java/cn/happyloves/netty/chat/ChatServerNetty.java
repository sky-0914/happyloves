package cn.happyloves.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author ZC
 * @date 2021/2/4 19:45
 */
public class ChatServerNetty {

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public ChatServerNetty() {
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    //socket参数，当服务器请求处理程全满时，用于临时存放已完成三次握手请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    //启用心跳保活机制，tcp，默认2小时发一次心跳
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder()); //向pipeline加入解码器
                            pipeline.addLast("encoder", new StringEncoder()); //加入编码器
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("netty服务器启动");
            ChannelFuture channelFuture = bootstrap.bind(9999).syncUninterruptibly();

            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }


    public static void main(String[] args) {
        new ChatServerNetty();
    }
}
