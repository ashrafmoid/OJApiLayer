package com.ashraf.ojapilayer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ashraf.ojapilayer"})
@EnableEnversRepositories
public class ApplicationStarter {
    @Value("#{'${kafka.topic.list}'.split(',')}")
    private List<String> topics;

    public static void main (String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.build();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Bean
    public ApplicationRunner runner(KafkaAdmin kafkaAdmin) {
        return args -> {
            AdminClient admin = AdminClient.create(kafkaAdmin.getConfig());
            Set<String> allExistingTopics = admin.listTopics().names().get();
            System.out.println("Existing topics " + allExistingTopics);
            List<NewTopic> DLTTopics = topics.stream()
                    .filter(dltTopic -> !allExistingTopics.contains(dltTopic + ".DLT"))
                    .map(topic -> new NewTopic(topic + ".DLT", 1, (short) 1))
                    .collect(Collectors.toList());
            List<NewTopic> newTopics = topics.stream()
                    .filter(normalTopic -> !allExistingTopics.contains(normalTopic))
                    .map(topic -> new NewTopic(topic , 1, (short) 1))
                    .collect(Collectors.toList());
            admin.createTopics(DLTTopics).all().get();
            admin.createTopics(newTopics).all().get();
        };
    }
}
