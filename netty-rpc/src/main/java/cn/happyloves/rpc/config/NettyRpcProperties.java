package cn.happyloves.rpc.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2021/3/4 23:38
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyRpcProperties {
    private int serverPort;
}
