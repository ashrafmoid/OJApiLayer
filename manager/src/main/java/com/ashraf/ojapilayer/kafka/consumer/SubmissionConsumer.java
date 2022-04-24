package com.ashraf.ojapilayer.kafka.consumer;

import com.ashraf.ojapilayer.DTO.KafkaSubmissionDTO;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.ashraf.ojapilayer.service.EvaluationService;
import com.ashraf.ojapilayer.service.SubmissionService;
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
   private final Gson gson;
   private final EvaluationService evaluationService;
   private final SubmissionService submissionService;

    @KafkaListener(topics = "${kafka.submission.topic}", groupId = "${kafka.consumer.submission.group}")
    public void Listen(
            @Payload String payload,
            @Header(value = KafkaHeaders.CORRELATION_ID, required = false) String correlationId
    ) {
        log.info("Received message for submission: {}, correlationId: {}", payload, correlationId);
        KafkaSubmissionDTO kafkaSubmissionDTO = gson.fromJson(payload, KafkaSubmissionDTO.class);
        Submission submission = submissionService.getSubmissionById(String.valueOf(kafkaSubmissionDTO.getSubmissionId()))
                .orElseThrow(() -> new RuntimeException("Submission not found for Id " + kafkaSubmissionDTO.getSubmissionId()));
        log.info("Submission is {}", submission);
        if (submission.getStatus() != SubmissionStatus.QUEUED) {
            log.info("Event for submission with id {} already processed", submission.getId());
            return;
        }
        try {
            submission.setStatus(SubmissionStatus.TESTING);
            submissionService.saveSubmission(submission);
            evaluationService.evaluateSubmission(kafkaSubmissionDTO);
        } catch (Exception e) {
            submission = submissionService.getSubmissionById(String.valueOf(submission.getId())).orElseThrow();
            submission.setStatus(SubmissionStatus.QUEUED);
            submissionService.saveSubmission(submission);
            throw e;
        }
    }
}
