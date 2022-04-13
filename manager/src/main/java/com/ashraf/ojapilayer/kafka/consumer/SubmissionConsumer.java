package com.ashraf.ojapilayer.kafka.consumer;

import com.ashraf.ojapilayer.entity.Submission;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class SubmissionConsumer {
   // private final ObjectMapper objectMapper;
   private final Gson gson;

    @KafkaListener(topics = "${kafka.submission.topic}", groupId = "${kafka.consumer.submission.group}")
    public void Listen(
            @Payload String payload,
            @Header(value = KafkaHeaders.CORRELATION_ID, required = false) String correlationId
    ) {
        log.info("Received message for submission: {}, correlationId: {}", payload, correlationId);
        Submission submission = gson.fromJson(payload, Submission.class);
        log.info("Submission is {}", submission);
    }
}
