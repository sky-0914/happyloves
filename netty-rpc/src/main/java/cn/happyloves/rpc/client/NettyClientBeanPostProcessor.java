package cn.happyloves.rpc.client;

import cn.happyloves.rpc.message.RpcMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Netty客户端Bean后置处理器
 * 实现Spring后置处理器接口：BeanPostProcessor
 * 在Bean对象在实例化和依赖注入完毕后，在显示调用初始化方法的前后添加我们自己的逻辑。注意是Bean实例化完毕后及依赖注入完成后触发的
 *
 * @author ZC
 * @date 2021/3/2 23:00
 */
@Slf4j
public class NettyClientBeanPostProcessor implements BeanPostProcessor {

    private final NettyClient nettyClient;

    public NettyClientBeanPostProcessor(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    /**
     * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到Bean实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    public Object postProcessBeforeInitialization(Object bean, @Nullable String beanName) throws BeansException {
        //获取实例Class
        Class<?> beanClass = bean.getClass();
        do {
            //获取该类所有字段
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                //判断该字段是否拥有@RpcServer
                if (field.getAnnotation(RpcServer.class) != null) {
                    field.setAccessible(true);
                    try {
                        //通过JDK动态代理获取该类的代理对象
                        Object o = Proxy.newProxyInstance(field.getType().getClassLoader(), new Class[]{field.getType()}, new ClientInvocationHandle(nettyClient));
                        //将代理类注入该字段
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

    /**
     * 实例化、依赖注入、初始化完毕时执行
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到Bean实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 可以根据beanName不同执行不同的处理操作
        return bean;
    }

    /**
     * 实例化、依赖注入、初始化完毕时执行
     * 注意：方法返回值不能为null
     * 如果返回null那么在后续初始化方法将报空指针异常或者通过getBean()方法获取不到bena实例对象
     * 因为后置处理器从Spring IoC容器中取出bean实例对象没有再次放回IoC容器中
     */

    /*private <T> T getBean(Class<T> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            //Netty
        });
    }*/

    /**
     * JDK动态代理处理器
     */
    static class ClientInvocationHandle implements InvocationHandler {
        private final NettyClient nettyClient;

        public ClientInvocationHandle(NettyClient nettyClient) {
            this.nettyClient = nettyClient;
        }

        /**
         * 代理方法调用
         *
         * @param proxy  代理类
         * @param method 方法
         * @param args   参数
         * @return 返回值
         */
        public Object invoke(Object proxy, Method method, Object[] args) {
            //组装Netty参数
            RpcMessage rpcMessage = RpcMessage.builder()
                    .name(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .parTypes(method.getParameterTypes())
                    .pars(args)
                    .build();
            //调用Netty，发送数据
            RpcMessage send = nettyClient.send(1111, rpcMessage);
            log.info("接收到服务端数据：{}", send);
            return send.getResult();
        }
    }
}
