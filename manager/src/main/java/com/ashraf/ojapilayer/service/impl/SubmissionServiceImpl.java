package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.DTO.KafkaSubmissionDTO;
import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.api.requestmodels.SubmissionRequest;
import com.ashraf.ojapilayer.constants.Constant;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.ashraf.ojapilayer.kafka.producer.KafkaProducer;
import com.ashraf.ojapilayer.repository.SubmissionRepository;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.SubmissionService;
import com.ashraf.ojapilayer.service.UserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class SubmissionServiceImpl implements SubmissionService {
    private final DocumentService documentService;
    private final SubmissionRepository submissionRepository;
    private final UserManagementService userManagementService;
    private final QuestionService questionService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Value("${kafka.submission.topic}")
    private String submissionTopic;

    @Override
    @Transactional
    public Submission submitSolution(SubmissionRequest request) {
        String fileId = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(request.getFile())
                .fileType(Constant.TEXT_PLAIN_APPLICATION_TYPE.toString())
                .build());
        UserProfile userProfile = userManagementService.findUserById(Long.valueOf(request.getAuthorId()));
        Question question = questionService.getQuestionById(Long.valueOf(request.getQuestionId()))
                .orElseThrow();
        Submission submission = Submission.builder().documentLink(fileId)
                .language(request.getLanguage())
                .author(userProfile)
                .question(question)
                .status(SubmissionStatus.QUEUED)
                .build();
        submission = submissionRepository.save(submission);
        try {
            kafkaProducer.sendMessage(submissionTopic, objectMapper.writeValueAsString(KafkaSubmissionDTO.builder()
                    .submissionId(submission.getId()).submissionDocumentLink(submission.getDocumentLink())
                    .questionId(request.getQuestionId()).build()));
        } catch (IOException e) {
            log.error("Exception value serializing submission {}", submission);
            throw new RuntimeException("Exception occurred while serializing value");
        }
        return submission;

    }

    @Override
    public Optional<Submission> getSubmissionById(String id) {
        return submissionRepository.findById(Long.valueOf(id));
    }
}
