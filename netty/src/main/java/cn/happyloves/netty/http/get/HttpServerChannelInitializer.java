package cn.happyloves.netty.http.get;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 本身也是个ChannelHandler，起始Handler
 * 为accept channel的pipeline预添加的handler
 * 给 pipeline 添加处理器，每当有连接accept时，就会运行到此处。
 *
 * @author zc
 * @date 2021/2/3 10:47
 */
public class HttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //可以在这里获取Channel，放入集合中管理所有客户端Channel。
        System.out.println("客户端 SocketChannel hashcode=" + socketChannel.hashCode());
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个 netty 提供的 httpServerCodec：codec => [coder - decoder]
        //1、HttpServerCodec 是 netty 提供的处理http的编解码器
        //HttpServerCodec只能获取uri中参数
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2、增加自定义的Handler
        pipeline.addLast("MyTestHttpServerHandler", new HttpServerHandler());

        System.out.println("====== HttpServerChannelInitializer is ok ... ======");
    }
}
