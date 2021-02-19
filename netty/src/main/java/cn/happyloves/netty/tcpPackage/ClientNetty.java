package cn.happyloves.netty.tcpPackage;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author zc
 * @date 2021/2/19 15:37
 */
public class ClientNetty {

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                for (int i = 0; i < 10; i++) {
                                    System.out.println("发送数据：" + i);
                                    ByteBuf byteBuf = Unpooled.copiedBuffer("==== " + i, CharsetUtil.UTF_8);
                                    ctx.writeAndFlush(byteBuf);
                                }
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

                            }
                        });
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).syncUninterruptibly();
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
