//package cn.happyloves.h2db.service;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * @author ZC
// * @date 2021/3/27 15:45
// */
//@Component
//public class TestConfig implements ApplicationContextAware, BeanPostProcessor {
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("ApplicationContextAware-setApplicationContext()");
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("BeanPostProcessor-postProcessBeforeInitialization()>>>>" + beanName + "=>" + bean);
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("BeanPostProcessor-postProcessAfterInitialization()>>>>" + beanName + "=>" + bean);
//        return bean;
//    }
//}
