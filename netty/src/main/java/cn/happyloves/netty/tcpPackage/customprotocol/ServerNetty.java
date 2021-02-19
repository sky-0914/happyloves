package cn.happyloves.netty.tcpPackage.customprotocol;

import cn.happyloves.netty.tcpPackage.customprotocol.coder.MessageDecoder;
import cn.happyloves.netty.tcpPackage.customprotocol.coder.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zc
 * @date 2021/2/19 11:07
 */
public class ServerNetty {

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new SimpleChannelInboundHandler<CustomMessage>() {
                                    @Override
                                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println(ctx.channel().remoteAddress() + "注册了");
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CustomMessage message) throws Exception {
                                        System.out.println("客户端发送的消息是：" + new String(message.getContent()) + " 长度：" + message.getLength());
                                    }
                                });
                    }
                });
        final ChannelFuture channelFuture = serverBootstrap.bind(9999).syncUninterruptibly();
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

}
