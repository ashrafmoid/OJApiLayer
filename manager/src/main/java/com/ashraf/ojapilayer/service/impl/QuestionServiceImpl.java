package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.api.requestmodels.Tags;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.mapper.QuestionMapper;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import com.ashraf.ojapilayer.repository.QuestionRepository;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;
    private final DocumentService documentService;
    private final QuestionRepository questionRepository;
    private final UserManagementService userManagementService;

    @Override
    public Question addQuestion(MultipartFile file, QuestionMetaData metaData) {
        final String id = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(file).fileType(metaData.getFileType()).build());
        final List<UserProfile> userProfiles = userManagementService.findAllUserById(metaData.getAuthorId());
        final Question question = Question.builder().documentLink(id)
                .rating(metaData.getRating())
                .topics(metaData.getTopics())
                .authors(userProfiles)
                .build();
        return questionRepository.save(question);

    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question> getQuestionsByTag(Tags tags) {
        return null;
    }
}
