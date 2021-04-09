package cn.happyloves.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZC
 * @date 2021/4/7 19:38
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        //设置客户端工作线程
        NioEventLoopGroup worker = new NioEventLoopGroup();
        //创建客户端启动对象
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                //通道连接者
                .channel(NioSocketChannel.class)
                //给worker线程组初始化处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //添加字符串的编解码器
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                //添加对象的编解码器，ClassResolvers.weakCachingConcurrentResolver设置弱引用WeakReferenceMap缓存类加载器，防止内存溢出
                                .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                .addLast(new ObjectEncoder())
                                //添加自定义的业务处理器
                                .addLast(new SimpleChannelInboundHandler<Object>() {

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        ctx.writeAndFlush("哈哈哈");
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                        log.info("客户端接收到的数据：{}", o.toString());
                                    }
                                });
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).syncUninterruptibly();
        channelFuture.channel().closeFuture();
    }

}
