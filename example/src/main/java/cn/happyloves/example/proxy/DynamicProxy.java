package cn.happyloves.example.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author zc
 * @date 2021/2/6 16:42
 */
public class DynamicProxy {

    public static void main(String[] args) {
        IGameService game = new GameServiceImpl();
        InvocationHandler dynamicProxyHandler = new DynamicProxyHandler<>(game);
        final IGameService proxy = (IGameService) Proxy.newProxyInstance(game.getClass().getClassLoader(), new Class[]{IGameService.class}, dynamicProxyHandler);
        System.out.println(proxy.getClass());
        proxy.login("张三", "111");
        proxy.playGame();
    }

}
