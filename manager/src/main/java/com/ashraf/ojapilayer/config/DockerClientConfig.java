package com.ashraf.ojapilayer.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {
    @Bean
    public DockerClient getDockerClientJava() {
        return DockerClientBuilder.getInstance().build();
    }
}
