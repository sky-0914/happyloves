package cn.happyloves.rabbitmq.producers.config.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

/**
 * @author zc
 * @date 2020/12/16 18:05
 */
@Slf4j
public class MyRecoveryCallback implements RecoveryCallback {
    @Override
    public Object recover(RetryContext retryContext) throws Exception {
        String messageStr = new ObjectMapper().writeValueAsString(retryContext);
        log.info("重试回调 - message: {}", messageStr);
        return retryContext;
    }
}
