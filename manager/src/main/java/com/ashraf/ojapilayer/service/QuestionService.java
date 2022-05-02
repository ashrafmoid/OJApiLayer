package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.DTO.PaginatedDTO;
import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.api.requestmodels.FilterQueryRequest;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface QuestionService {
    Question addQuestion(AddQuestionRequest request);
    Optional<Question> getQuestionById(Long id);
    Question addTestFile(String questionId, MultipartFile testFile);
    Question addOutputFile(String questionId, MultipartFile outputFile);
    PaginatedDTO<QuestionDTO> getAllQuestionsForPage(Integer pageNumber, Integer size);
    PaginatedDTO<QuestionDTO> getAllQuestionByFilter(FilterQueryRequest filterQueryRequest);
    Question updateQuestionMetaData(QuestionMetaData questionMetaData, String questionId);
}
