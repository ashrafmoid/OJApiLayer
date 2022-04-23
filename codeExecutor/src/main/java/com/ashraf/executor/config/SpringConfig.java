package com.ashraf.executor.config;

import com.ashraf.executor.factory.CodeHandlerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ashraf.executor")
public class SpringConfig {
    @Bean
    public FactoryBean<?> codeHandlerFactory() {
        final ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(CodeHandlerFactory.class);
        return serviceLocatorFactoryBean;
    }
}
