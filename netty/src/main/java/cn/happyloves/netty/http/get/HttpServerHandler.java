package cn.happyloves.netty.http.get;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2021/2/3 11:16
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据。
     *
     * @param channelHandlerContext 上下文对象
     * @param httpObject            客户端和服务器端互相通讯的数据被封装成 HttpObject
     * @throws Exception 异常信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        //判断 msg 是不是 HTTPRequest 请求
        if (httpObject instanceof HttpRequest) {
            System.out.println("msg 类型 = " + httpObject.getClass());
            System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());

            HttpRequest request = (HttpRequest) httpObject;
            String path = request.uri();            //获取路径
            HttpMethod method = request.method();//获取请求方法
            //获取uri，进行路径过滤
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico，不做响应");
                //该方式会将任务提交到taskQueue队列中。提交到该队列中的任务会按照提交顺序依次执行。
                channelHandlerContext.channel().eventLoop().execute(() -> {
                    log.info("执行任务队列1。。。");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                channelHandlerContext.channel().eventLoop().execute(() -> {
                    log.info("执行任务队列2。。。");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                //该方式会将任务提交到scheduleTaskQueue定时任务队列中。该队列是底层是优先队列PriorityQueue实现的
                channelHandlerContext.channel().eventLoop().schedule(() -> {
                    log.info("执行延迟队列1。。。");
                }, 10, TimeUnit.SECONDS);
                channelHandlerContext.channel().eventLoop().schedule(() -> {
                    log.info("执行延迟队列2。。。");
                }, 10, TimeUnit.SECONDS);

                return;
            } else {
                System.out.println("请求URL：" + request.uri());
                System.out.println("请求头：" + request.headers());
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("HelloWorld", CharsetUtil.UTF_8);
            //构造一个http的响应，即HTTPResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //将构建好的 response 返回
            channelHandlerContext.writeAndFlush(response);
        }
    }
}
