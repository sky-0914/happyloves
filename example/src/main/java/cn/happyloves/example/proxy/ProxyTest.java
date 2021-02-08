package cn.happyloves.example.proxy;

import cn.happyloves.example.proxy.cglib.CglibHandler;
import cn.happyloves.example.proxy.dynamic.GameServiceImpl;
import cn.happyloves.example.proxy.dynamic.IGameService;
import cn.happyloves.example.proxy.dynamic.ProxyHandler;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author zc
 * @date 2021/2/8 17:55
 */
public class ProxyTest {
    public static void main(String[] args) {
        IGameService game = new GameServiceImpl();
        InvocationHandler dynamicProxyHandler = new ProxyHandler<>(game);
        final IGameService proxy = (IGameService) Proxy.newProxyInstance(game.getClass().getClassLoader(), new Class[]{IGameService.class}, dynamicProxyHandler);
        System.out.println(proxy.getClass());
        proxy.login("张三", "111");
        proxy.playGame();
        System.out.println("================= Cglib =================");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GameServiceImpl.class);
        enhancer.setCallback(new CglibHandler<>(new GameServiceImpl()));
        final GameServiceImpl proxy2 = (GameServiceImpl) enhancer.create();
        System.out.println(proxy2.getClass());
        proxy2.login("张三", "111");
        proxy2.playGame();
    }
}
