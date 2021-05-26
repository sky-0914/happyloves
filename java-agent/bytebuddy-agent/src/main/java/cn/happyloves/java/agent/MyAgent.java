package cn.happyloves.java.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * ByteBuddy不仅仅是为了生成Java-Agent，它提供的API甚至可以改变重写一个Java类
 *
 * @author zc
 * @date 2021/5/26 17:35
 */
public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is an perform monitor agent.");

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                /**
                 * 通过AgentBuilder方法，生成一个Agent。
                 * 这里有两点需要特别说明：
                 * 其一是在AgentBuilder.type处，这里可以指定需要拦截的类；
                 * 其二是在builder.method处，这里可以指定需要拦截的方法。
                 * 当然其API支持各种isStatic、isPublic等等一系列方式。
                 */
                return builder.method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(TimeInterceptor.class)); // 委托
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith(agentArgs))
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }

    /**
     * 在上一步实现Transformer的过程中，委托了一个TimeInterceptor.class。
     * 下面是其实现方式，整个的try语句是原有的代码执行，我们在之前打了时间戳，并在其结束后，计算并打印了其调用耗时。
     */
    static class TimeInterceptor {
        @RuntimeType
        public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
            long start = System.currentTimeMillis();
            try {
                // 原有函数执行
                return callable.call();
            } finally {
                System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
            }
        }
    }
}
