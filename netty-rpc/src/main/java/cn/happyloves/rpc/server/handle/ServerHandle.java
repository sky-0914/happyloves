package cn.happyloves.rpc.server.handle;

import cn.happyloves.rpc.message.RpcMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ZC
 * @date 2021/3/1 22:15
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerHandle extends SimpleChannelInboundHandler<RpcMessage> implements ApplicationContextAware {
    private Map<String, Object> serviceMap;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serviceMap = applicationContext.getBeansWithAnnotation(Service.class);
        log.info("被@Service注解加载的Bean: {}", serviceMap);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接了: {}", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        log.info("客户端发送的消息：{}", rpcMessage);
        Object service = serviceMap.get(rpcMessage.getName());
        Method method = service.getClass().getMethod(rpcMessage.getMethodName(), rpcMessage.getParTypes());
        method.setAccessible(true);
        Object result = method.invoke(service, rpcMessage.getPars());
        rpcMessage.setResult(result);
        channelHandlerContext.channel().writeAndFlush(rpcMessage);
    }
}
