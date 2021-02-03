package cn.happyloves.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zc
 * @date 2021/2/2 16:50
 */
@Slf4j
public class ClientNetty {

    public static void main(String[] args) {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            //注意：客户端使用的不是 ServerBootStrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    //通道连接者,实例化一个channel
                    .channel(NioSocketChannel.class)
                    //通道处理者,进行通道初始化配置
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientChannelHandler());
                        }
                    })
                    //心跳报活
                    .option(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("客户端 OK...");
            //启动客户端去连接服务器端
            //关于 channelFuture 涉及到 netty 的异步模型
            final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).syncUninterruptibly();
            if (channelFuture.isSuccess()) {
                final Channel channel = channelFuture.channel();
                log.info("connect tcp client, channel = {}", channel);
            } else {
                log.error("connect tcp client fail");
            }
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }

    }
}
