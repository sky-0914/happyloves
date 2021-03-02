package cn.happyloves.rpc.config;

import cn.happyloves.rpc.client.NettyClient;
import cn.happyloves.rpc.client.NettyClientBeanPostProcessor;
import cn.happyloves.rpc.client.handle.ClientHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 * @date 2021/3/1 18:24
 */
@Configuration
public class ClientBeanConfig {

    @Bean
    public NettyClient nettyClient() {
        return new NettyClient();
    }

    @Bean
    public ClientHandle clientHandle() {
        return new ClientHandle();
    }

    @Bean
    public NettyClientBeanPostProcessor nettyClientBeanPostProcessor(NettyClient nettyClient, ClientHandle clientHandle) {
        return new NettyClientBeanPostProcessor(nettyClient, clientHandle);
    }
}
