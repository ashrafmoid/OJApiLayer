package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.api.requestmodels.Tags;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {
    QuestionDTO addQuestion(MultipartFile file, QuestionMetaData metaData);
    QuestionDTO getQuestionById(Long id);
    // this needs to be paginated
    List<Question> getQuestionsByTag(Tags tags);

}
