package cn.happyloves.rpc.client.handle;

import cn.happyloves.rpc.message.RpcMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * @author zc
 * @date 2021/3/2 15:19
 */
@Slf4j
@ChannelHandler.Sharable
//public class ClientHandle extends SimpleChannelInboundHandler<RpcMessage> implements ApplicationContextAware {
public class ClientHandle extends SimpleChannelInboundHandler<RpcMessage> {
    private ConcurrentMap<Channel, RpcMessage> rpcMessageConcurrentMap;

    public ClientHandle(ConcurrentMap<Channel, RpcMessage> rpcMessageConcurrentMap) {
        this.rpcMessageConcurrentMap = rpcMessageConcurrentMap;
    }

//    private <T> T getBean(Class<T> clazz) {
//        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
//
//        });
//    }
//
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        //获取所有被Spring容器加载的Bean
//        final String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String name : beanDefinitionNames) {
//            //获取Bean的Class
//            Class<?> clazz = applicationContext.getType(name);
//            if (clazz != null) {
//                //获取所有属性
//                Field[] fields = clazz.getDeclaredFields();
//                for (Field f : fields) {
//                    //判断该属性是否有自定义注解
//                    RpcServer rpcServer = f.getAnnotation(RpcServer.class);
//                    if (rpcServer != null) {
//                        //获取该属性的Class
//                        Class<?> fieldClass = f.getType();
//                        //获取对象
//                        Object object = applicationContext.getBean(name);
//                        f.setAccessible(true);
//                        try {
//                            f.set(object, clientProxyFactory.getProxy(fieldClass));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//        }
//    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress());
//        for (int i = 0; i < 10; i++) {
//            RpcMessage rpcMessage = new RpcMessage();
//            rpcMessage.setName("cn.happyloves.rpc.api.Test1Api");
//            rpcMessage.setMethodName("testStr");
//            rpcMessage.setParTypes(new Class[]{int.class});
//            rpcMessage.setPars(new Object[]{1});
//            System.out.println(rpcMessage);
//            channelHandlerContext.channel().writeAndFlush(rpcMessage);
//            System.out.println("================");
//        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage) throws Exception {
        log.info("客户端收到服务端消息：{}", rpcMessage);
        rpcMessageConcurrentMap.put(channelHandlerContext.channel(), rpcMessage);
    }
}
