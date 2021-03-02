package cn.happyloves.rpc.client.handle;

import cn.happyloves.rpc.client.RpcServer;
import cn.happyloves.rpc.message.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zc
 * @date 2021/3/2 15:19
 */
public class ClientHandle extends SimpleChannelInboundHandler<RpcMessage> implements ApplicationContextAware {

    private <T> T getBean(Class<T> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {

        });
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        final String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            Class<?> clazz = applicationContext.getType(name);
            if (clazz != null) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    RpcServer rpcServer = f.getAnnotation(RpcServer.class);
                    if (rpcServer != null) {
                        Class<?> fieldClass = f.getType();
                        Object object = applicationContext.getBean(name);
                        f.setAccessible(true);
                        try {
                            f.set(object, clientProxyFactory.getProxy(fieldClass));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {

    }

    static class ClientInvocationHandle implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            return null;
        }
    }
}
