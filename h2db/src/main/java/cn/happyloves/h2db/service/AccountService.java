package cn.happyloves.h2db.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author ZC
 * @date 2021/3/1 21:14
 */
@Slf4j
@Service
public class AccountService implements ApplicationContextAware, InitializingBean, BeanPostProcessor {
    @Autowired
    private TestService testService;

    public AccountService() {
        System.out.println("Account-构造方法");
    }

    public void test() {
        System.out.println("AccountService=====》》》》》test()");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware-setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean-afterPropertiesSet");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("BeanPostProcessor-postProcessBeforeInitialization");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("BeanPostProcessor-postProcessAfterInitialization");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
