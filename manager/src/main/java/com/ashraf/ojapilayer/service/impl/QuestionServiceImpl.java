package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.DTO.PaginatedDTO;
import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.api.requestmodels.FilterQueryRequest;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.enums.Topic;
import com.ashraf.ojapilayer.mapper.QuestionMapper;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import com.ashraf.ojapilayer.repository.QuestionRepository;
import com.ashraf.ojapilayer.resolver.QueryResolver;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;
    private final DocumentService documentService;
    private final QuestionRepository questionRepository;
    private final UserManagementService userManagementService;
    private final QueryResolver queryResolver;
    private Integer defaultPage =0;
    private Integer defaultPageSize = 40;

    @Override
    @Transactional
    public Question addQuestion(AddQuestionRequest request) {
        final String id = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(request.getTestFile()).fileType(request.getQuestionMetaData().getFileType()).build());
        final List<UserProfile> userProfiles = userManagementService.findAllUserById(request.getQuestionMetaData().getAuthorIds());
        final Question question = Question.builder().questionFileLink(id)
                .rating(request.getQuestionMetaData().getRating())
                .topics(request.getQuestionMetaData().getTopics().stream().map(Topic::getValue).collect(Collectors.toList()))
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
    public PaginatedDTO<QuestionDTO> getAllQuestionsForPage(Integer pageNumber, Integer size) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Order.asc("questionName")));
        Page<Question> questionPage = questionRepository.findAll(pageable);
        List<QuestionDTO> questionDTOList = questionPage.getContent().stream()
                .map(questionMapper::questionToQuestionDTO)
                .collect(Collectors.toList());
        return PaginatedDTO.<QuestionDTO>builder().content(questionDTOList)
                .pageNumber(questionPage.getNumber())
                .size(questionPage.getSize())
                .isLast(questionPage.isLast())
                .totalElements(questionPage.getNumberOfElements())
                .totalPages(questionPage.getTotalPages())
                .build();
    }

    @Override
    public PaginatedDTO<QuestionDTO> getAllQuestionByFilter(FilterQueryRequest filterQueryRequest) {
        Pageable pageable = PageRequest.of(filterQueryRequest.getPageNumber(), filterQueryRequest.getSize());
        PaginatedDTO<Question> questionPage = queryResolver.getResultsForQuery(filterQueryRequest.getQuery(), Question.class, pageable);
        List<QuestionDTO> questionDTOList = questionPage.getContent().stream().map(questionMapper::questionToQuestionDTO).collect(Collectors.toList());
        return PaginatedDTO.<QuestionDTO>builder().content(questionDTOList)
                .pageNumber(questionPage.getPageNumber())
                .size(questionPage.getSize())
                .isLast(questionPage.isLast())
                .totalElements(questionPage.getTotalElements())
                .totalPages(questionPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public Question updateQuestionMetaData(QuestionMetaData questionMetaData, String questionId) {
        Question question = questionRepository.findById(Long.valueOf(questionId))
                .orElseThrow(() -> new InvalidRequestException("No question found for Id " + questionId));
        List<UserProfile> authors = userManagementService.findAllUserById(questionMetaData.getAuthorIds());
        if (CollectionUtils.isNotEmpty(authors)) {
            question.setAuthors(authors);
        }
        if(Objects.nonNull(questionMetaData.getQuestionName())) {
            question.setQuestionName(questionMetaData.getQuestionName());
        }
        if (Objects.nonNull(questionMetaData.getRating())) {
            question.setRating(questionMetaData.getRating());
        }
        if (CollectionUtils.isNotEmpty(questionMetaData.getTopics())) {
            question.setTopics(questionMetaData.getTopics().stream().map(Topic::getValue).collect(Collectors.toList()));
        }
        return questionRepository.save(question);
    }
}
