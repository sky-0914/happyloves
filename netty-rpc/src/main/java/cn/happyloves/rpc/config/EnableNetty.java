package cn.happyloves.rpc.config;

import cn.happyloves.rpc.client.EnableNettyClient;
import cn.happyloves.rpc.server.EnableNettyServer;

import java.lang.annotation.*;

/**
 * @author ZC
 * @date 2021/3/25 11:28
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableNettyClient
@EnableNettyServer
public @interface EnableNetty {
}
