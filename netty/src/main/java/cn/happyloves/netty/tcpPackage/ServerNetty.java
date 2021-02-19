package cn.happyloves.netty.tcpPackage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

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
                                .addLast(new SimpleChannelInboundHandler<Object>() {
                                    @Override
                                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println(ctx.channel().remoteAddress() + "注册了");
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                        ByteBuf buf = (ByteBuf) o;
                                        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
                                    }
                                });
                    }
                });
        final ChannelFuture channelFuture = serverBootstrap.bind(9999).syncUninterruptibly();
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

}
