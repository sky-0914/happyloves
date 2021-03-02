package cn.happyloves.rpc.client;

import java.lang.annotation.*;

/**
 * @author zc
 * @date 2021/3/2 15:36
 */
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcServer {
}
