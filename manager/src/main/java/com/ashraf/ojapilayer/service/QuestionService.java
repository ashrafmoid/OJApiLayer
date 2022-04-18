package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.api.requestmodels.Tags;
import com.ashraf.ojapilayer.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question addQuestion(AddQuestionRequest request);
    Optional<Question> getQuestionById(Long id);
    Question addTestFile(String questionId, MultipartFile testFile);
    // this needs to be paginated
    List<Question> getQuestionsByTag(Tags tags);

}
