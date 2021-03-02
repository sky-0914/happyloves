package cn.happyloves.rpc.client;

import cn.happyloves.rpc.client.handle.ClientHandle;
import cn.happyloves.rpc.message.RpcMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 在 Bean 完成实例化后增加自己的处理逻辑
 *
 * @author ZC
 * @date 2021/3/2 23:00
 */
@Slf4j
public class NettyClientBeanPostProcessor implements BeanPostProcessor {

    private NettyClient nettyClient;
    private ClientHandle clientHandle;

    public NettyClientBeanPostProcessor(NettyClient nettyClient,ClientHandle clientHandle) {
        this.nettyClient = nettyClient;
        this.clientHandle = clientHandle;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("在 Bean 完成实例化后增加自己的处理逻辑 ===>>> {}", beanName);
        Class beanClass = bean.getClass();
        do {
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(RpcServer.class) != null) {
                    field.setAccessible(true);
                    try {
                        Object o = Proxy.newProxyInstance(field.getType().getClassLoader(), new Class[]{field.getType()}, new ClientInvocationHandle(nettyClient,clientHandle));
                        field.set(bean, o);
                        log.info("创建代理类 ===>>> {}", beanName);
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        } while ((beanClass = beanClass.getSuperclass()) != null);
        return bean;
    }

//    private <T> T getBean(Class<T> clazz) {
//        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
//            //Netty
//        });
//    }

    static class ClientInvocationHandle implements InvocationHandler {
        private NettyClient nettyClient;
        private ClientHandle clientHandle;

        public ClientInvocationHandle(NettyClient nettyClient,ClientHandle clientHandle) {
            this.nettyClient = nettyClient;
            this.clientHandle = clientHandle;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcMessage rpcMessage = RpcMessage.builder()
                    .name(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .parTypes(method.getParameterTypes())
                    .pars(args)
                    .build();
            RpcMessage send = nettyClient.send(1111, rpcMessage,clientHandle);
            log.info("接收到服务端数据：{}", send);
            return send.getResult();
        }
    }
}
