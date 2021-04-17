package cn.happyloves.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author ZC
 * @date 2021/4/7 19:38
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new StringDecoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                        System.out.println(s);
                                    }
                                });
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).syncUninterruptibly();
        Channel channel = channelFuture.channel();
        //客户端需要输入信息，创建一个扫描器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            //通过channel发送到服务器端
            channel.writeAndFlush(msg + "\r\n");
        }

        ChannelFuture channelFuture1 = channel.closeFuture().syncUninterruptibly();
    }

}
