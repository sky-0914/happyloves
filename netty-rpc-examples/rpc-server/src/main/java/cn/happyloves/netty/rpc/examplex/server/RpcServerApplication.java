package cn.happyloves.netty.rpc.examplex.server;

import cn.happyloves.rpc.config.EnableNetty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZC
 * @date 2021/3/2 23:55
 */
@EnableNetty
@SpringBootApplication
public class RpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }
}
