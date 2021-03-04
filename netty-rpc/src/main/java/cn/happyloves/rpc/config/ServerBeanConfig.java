package cn.happyloves.rpc.config;

import cn.happyloves.rpc.server.NettyServer;
import cn.happyloves.rpc.server.handle.ServerHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * NettyServer服务端配置类
 *
 * @author zc
 * @date 2021/3/1 18:24
 */
@Configuration
@EnableConfigurationProperties(NettyRpcProperties.class)
public class ServerBeanConfig {

    private final NettyRpcProperties nettyRpcProperties;

    @Autowired
    public ServerBeanConfig(NettyRpcProperties nettyRpcProperties) {
        this.nettyRpcProperties = nettyRpcProperties;
    }

    /**
     * 配置ServerHandle
     *
     * @return ServerHandle处理类
     */
    @Bean
    public ServerHandle serverHandle() {
        return new ServerHandle();
    }

    /**
     * 配置NettyServer
     *
     * @param handle ServerHandle处理类
     * @return NettyServer
     */
    @Bean
    public NettyServer nettyServer(ServerHandle handle) {
        NettyServer nettyServer = new NettyServer(handle);
        nettyServer.start(nettyRpcProperties.getServerPort());
        return nettyServer;
    }
}
