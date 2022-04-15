package com.ashraf.ojapilayer.config;


import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {
    @Bean
    public DockerClient getDockerClientJava() throws  DockerCertificateException {
     return DefaultDockerClient.fromEnv().build();
    }
}
