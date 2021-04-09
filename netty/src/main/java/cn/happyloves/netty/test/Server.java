package cn.happyloves.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;


/**
 * @author ZC
 * @date 2021/4/7 19:19
 */
@Slf4j
public class Server {

    public static void main(String[] args) {
        //创建连接线程组，线程数为1。只负责处理连接请求
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        //创建工作线程组，线程数默认为cpu核数*2。处理与客户端的业务处理
        NioEventLoopGroup worker = new NioEventLoopGroup();
        //创建Server端的启动对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //配置线程组
        serverBootstrap.group(boss, worker)
                //使用 NioServerSocketChannel 作为服务器的通道实现
                .channel(NioServerSocketChannel.class)
                //给worker线程组初始化处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
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
                                        log.info("客户端连接啦。。。客户端地址：{}", ctx.channel().remoteAddress());
                                    }
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                        log.info("服务端接收到的数据：{}", o.toString());
                                        channelHandlerContext.writeAndFlush("嘿嘿嘿");
                                    }
                                });
                    }
                });
        //启动并且监听
        ChannelFuture channelFuture = serverBootstrap.bind(8888).syncUninterruptibly();
        //监听关闭通道
        channelFuture.channel().closeFuture();
    }

}