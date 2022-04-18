package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.DTO.KafkaSubmissionDTO;

public interface EvaluationService {
    void evaluateSubmission(KafkaSubmissionDTO kafkaSubmissionDTO);
}
