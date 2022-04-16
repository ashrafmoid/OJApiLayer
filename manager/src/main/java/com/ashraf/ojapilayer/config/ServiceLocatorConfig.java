//package com.ashraf.ojapilayer.config;
//
//import com.ashraf.ojapilayer.factory.CodeHandlerFactory;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ServiceLocatorConfig {
//    @Bean
//    public FactoryBean<?> codeHandlerFactory() {
//        final ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
//        serviceLocatorFactoryBean.setServiceLocatorInterface(CodeHandlerFactory.class);
//        return serviceLocatorFactoryBean;
//    }
//}
