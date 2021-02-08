package cn.happyloves.example.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式-JDK动态代理
 * 实现JDK，InvocationHandler接口（反射调用处理器）
 * Spring Framework中默认使用JDK+Cglib配合使用，默认使用JDK代理，只有当实例没有实现接口时才使用Cglib代理
 * 只能代理实现了接口的实例对象
 * java.lang.reflect.Proxy.newProxyInstance(game.getClass().getClassLoader(), new Class[]{IGameService.class}, dynamicProxyHandler);
 * game.getClass().getClassLoader()：被代理实例对象的ClassLoader（类加载器）
 * new Class[]{IGameService.class}： 接口的Class
 * dynamicProxyHandler：             代理类实例对象
 *
 * @author zc
 * @date 2021/2/6 16:42
 */
@Slf4j
public class ProxyHandler<T> implements InvocationHandler {
    private T t;

    public ProxyHandler(T t) {
        this.t = t;
    }

    /**
     * 调用增强
     *
     * @param proxy  代理类
     * @param method 具体方法
     * @param args   参数
     * @return 调用返回值
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("proxy: {},method: {},args: {}", proxy, method, args);
        log.info("我是代理类===>>>执行前置业务");
        final Object invoke = method.invoke(this.t, args);
        log.info("我是代理类===>>>执行后置业务");
        return invoke;
    }

    /**
     * java.lang.reflect.Proxy.newProxyInstance(game.getClass().getClassLoader(), new Class[]{IGameService.class}, dynamicProxyHandler);
     * game.getClass().getClassLoader()：被代理实例对象的ClassLoader（类加载器）
     * new Class[]{IGameService.class}： 接口的Class
     * dynamicProxyHandler：             代理类实例对象
     */
    public static void main(String[] args) {
        IGameService game = new GameServiceImpl();
        InvocationHandler dynamicProxyHandler = new ProxyHandler<>(game);
        final IGameService proxy = (IGameService) Proxy.newProxyInstance(game.getClass().getClassLoader(), new Class[]{IGameService.class}, dynamicProxyHandler);
        System.out.println(proxy.getClass());
        proxy.login("张三", "111");
        proxy.playGame();
    }
}
