package cn.happyloves.rpc.config;

import cn.happyloves.rpc.server.NettyServer;
import cn.happyloves.rpc.server.handle.ServerHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2021/3/1 18:24
 */
@Configuration
public class ServerBeanConfig {

    @Bean
    public ServerHandle serverHandle() {
        return new ServerHandle();
    }

    @Bean
    public NettyServer nettyServer(ServerHandle handle) {
        NettyServer nettyServer = new NettyServer(1111, handle);
        nettyServer.start(1111);
        return nettyServer;
    }
}
