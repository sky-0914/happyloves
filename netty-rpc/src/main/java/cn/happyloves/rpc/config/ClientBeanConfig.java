package cn.happyloves.rpc.config;

import cn.happyloves.rpc.client.NettyClient;
import cn.happyloves.rpc.client.NettyClientBeanPostProcessor;
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
    public NettyClientBeanPostProcessor nettyClientBeanPostProcessor(NettyClient nettyClient) {
        return new NettyClientBeanPostProcessor(nettyClient);
    }
}
