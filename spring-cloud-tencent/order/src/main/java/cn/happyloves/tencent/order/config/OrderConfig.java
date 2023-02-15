package cn.happyloves.tencent.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zc
 * @date 2023/2/9 16:25
 */
@Data
@Component
//@RefreshScope //如果使用反射模式，则不需要加这个注解
//@Configuration
@ConfigurationProperties(prefix = "order")
public class OrderConfig implements Serializable {

    private String a;
    private int b;
    private String c;
}
