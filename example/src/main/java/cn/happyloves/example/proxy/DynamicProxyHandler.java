package cn.happyloves.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zc
 * @date 2021/2/6 16:42
 */
public class DynamicProxyHandler<T> implements InvocationHandler {
    private T t;

    public DynamicProxyHandler(T t) {
        this.t = t;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是代理类===>>>执行前置业务");
        final Object invoke = method.invoke(this.t, args);
        System.out.println("我是代理类===>>>执行后置业务");
        return invoke;
    }
}
