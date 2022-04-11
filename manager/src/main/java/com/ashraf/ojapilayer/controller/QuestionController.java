package com.ashraf.ojapilayer.controller;

import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import com.ashraf.ojapilayer.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addQuestion(@RequestPart(value = "file")MultipartFile file,
                                         @RequestPart(value = "metaData") QuestionMetaData metaData) {
        QuestionDTO questionDTO = questionService.addQuestion(file, metaData);
        return ResponseEntity.ok(questionDTO);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable("questionId") Long questionId) {
         return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }



}
