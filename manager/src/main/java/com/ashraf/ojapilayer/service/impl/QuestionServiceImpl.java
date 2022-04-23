package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.api.requestmodels.Tags;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.mapper.QuestionMapper;
import com.ashraf.ojapilayer.repository.QuestionRepository;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;
    private final DocumentService documentService;
    private final QuestionRepository questionRepository;
    private final UserManagementService userManagementService;

    @Override
    @Transactional
    public Question addQuestion(AddQuestionRequest request) {
        final String id = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(request.getTestFile()).fileType(request.getQuestionMetaData().getFileType()).build());
        final List<UserProfile> userProfiles = userManagementService.findAllUserById(request.getQuestionMetaData().getAuthorId());
        final Question question = Question.builder().questionFileLink(id)
                .rating(request.getQuestionMetaData().getRating())
                .topics(request.getQuestionMetaData().getTopics())
                .authors(userProfiles)
                .build();
        return questionRepository.save(question);

    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    @Transactional
    public Question addTestFile(String questionId, MultipartFile testFile) {
        final Question question = questionRepository.findById(Long.valueOf(questionId))
                .orElseThrow(() -> new RuntimeException("Question not found for id " + questionId));
        String id = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(testFile).fileType("txt")
                .build());
        question.setTestFileLink(id);
        return questionRepository.save(question);
    }

    @Override
    public Question addOutputFile(String questionId, MultipartFile outputFile) {
        final Question question = questionRepository.findById(Long.valueOf(questionId))
                .orElseThrow(() -> new RuntimeException("Question not found for id " + questionId));
        String id = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(outputFile).fileType("txt")
                .build());
        question.setCorrectOutputFileLink(id);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestionsByTag(Tags tags) {
        return null;
    }
}
