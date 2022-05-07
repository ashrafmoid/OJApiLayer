package com.ashraf.executor.config;

import com.ashraf.executor.factory.CodeHandlerFactory;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan("com.ashraf.executor")
public class SpringConfig {
    @Bean
    public FactoryBean<?> codeHandlerFactory() {
        final ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(CodeHandlerFactory.class);
        return serviceLocatorFactoryBean;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public DefaultExecutor defaultExecutor() {
        return new DefaultExecutor();
    }
}
