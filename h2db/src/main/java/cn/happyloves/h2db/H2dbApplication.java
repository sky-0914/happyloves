package cn.happyloves.h2db;

import cn.happyloves.rpc.server.EnableNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableNettyServer
@SpringBootApplication
public class H2dbApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2dbApplication.class, args);
    }

}
