package cn.happyloves.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zc
 * @date 2021/2/2 11:40
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        //创建BossGroup 和 WorkerGroup
        //1、创建两个线程组，bossGroup 和 workerGroup
        //2、bossGroup 只是处理连接请求，真正的和客户端业务处理，会交给 workerGroup 完成
        //3、两个都是无限循环
        //4、bossGroup 和 workerGroup 含有的子线程（NioEventLoop）个数为实际 cpu 核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程来进行设置，配置设置两个线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    //使用 NioServerSocketChannel 作为服务器的通道实现
                    //构造channel通道工厂//bossGroup的通道，只是负责连接,
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    //socket参数，当服务器请求处理程全满时，用于临时存放已完成三次握手请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置保持活动连接状态
                    //启用心跳保活机制，tcp，默认2小时发一次心跳
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .handler()//这个是给bossGroup添加的处理器
                    //为accept channel的pipeline预添加的handler
                    //设置通道处理者ChannelHandler////workerGroup的处理器
                    //给workerGroup 添加处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给 pipeline 添加处理器，每当有连接accept时，就会运行到此处。
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            final ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加处理器
                            pipeline.addLast(new ServerChannelHandler());
                        }
                    });//给我们的 workerGroup 的 EventLoop 对应的管道设置处理器
            System.out.println("Netty Server is Ready ...");
            //Future：异步任务的生命周期，可用来获取任务结果//绑定端口，开启监听，同步等待
            //绑定一个端口并且同步，生成了一个ChannelFuture 对象
            //启动服务器（并绑定端口）
            ChannelFuture future = serverBootstrap.bind(9999).syncUninterruptibly();
            if (future.isSuccess()) {
                final Channel channel = future.channel();
                //获取通道
                log.info("Netty tcp server start success, channel = {}", channel);
            } else {
                log.error("Netty tcp server start fail");
            }
            //对关闭通道进行监听
            future.channel().closeFuture().syncUninterruptibly();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
