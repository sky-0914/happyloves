package cn.happyloves.netty.tcpPackage.customprotocol;

import cn.happyloves.netty.tcpPackage.customprotocol.coder.MessageDecoder;
import cn.happyloves.netty.tcpPackage.customprotocol.coder.MessageEncoder;
import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

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
                        socketChannel.pipeline()
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new SimpleChannelInboundHandler<CustomMessage>() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        for (int i = 0; i < 10; i++) {
                                            System.out.println("发送数据：" + i);
//                                            final byte[] bytes = ("==== " + i).getBytes(StandardCharsets.UTF_8);

                                            final byte[] bytes = JSONUtil.toJsonStr(TestVO1.builder()
                                                    .id(i)
                                                    .name("张三")
                                                    .age(i + 18)
                                                    .list(Collections.singletonList("===" + i))
                                                    .build()).getBytes(StandardCharsets.UTF_8);

                                            final CustomMessage customMessage = new CustomMessage();
                                            customMessage.setLength(bytes.length);
                                            customMessage.setContent(bytes);
                                            ctx.writeAndFlush(customMessage);
                                        }
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CustomMessage message) throws Exception {

                                    }
                                });
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).syncUninterruptibly();
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
