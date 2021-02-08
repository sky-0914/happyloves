package cn.happyloves.example.proxy.cglib;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理-Cglib代理
 * 实现org.springframework.cglib.proxy.MethodInterceptor接口（反射调用处理器）
 * Spring Framework中默认使用JDK+Cglib配合使用，默认使用JDK代理，只有当实例没有实现接口时才使用Cglib代理
 * Cglib可以代理没有实现接口的实例对象
 * SpringBoot2.0默认使用Cglib代理
 *
 * @author zc
 * @date 2021/2/7 11:57
 */
@Slf4j
public class CglibHandler<T> implements MethodInterceptor {
    private T t;

    public CglibHandler(T t) {
        this.t = t;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("o:{},method:{},object:{},methodProxy:{}", o.getClass(), method, objects, methodProxy);
        log.debug("前置================================");
        final Object result = method.invoke(t, objects);
        log.debug("================================后置");
        return result;
    }

    /**
     *
     */
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LoveService.class);
        enhancer.setCallback(new CglibHandler<>(new LoveService()));
        final LoveService proxy = (LoveService) enhancer.create();
        System.out.println(proxy.getClass());
        proxy.dateWith("张三");
        proxy.hug();
    }
}
