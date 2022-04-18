package com.ashraf.ojapilayer.controller;

import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.mapper.QuestionMapper;
import com.ashraf.ojapilayer.models.QuestionMetaData;
import com.ashraf.ojapilayer.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final QuestionMapper questionMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addQuestion(@RequestPart(value = "file")MultipartFile file,
                                         @RequestPart(value = "metaData") QuestionMetaData metaData,
                                         @RequestPart(value = "testFile", required = false) MultipartFile testFile) {
        QuestionDTO questionDTO = questionMapper.questionToQuestionDTO(
                questionService.addQuestion(AddQuestionRequest.builder().questionFile(file)
                        .questionMetaData(metaData)
                        .testFile(testFile).build()));
        return ResponseEntity.ok(questionDTO);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable("questionId") Long questionId) {
         return ResponseEntity.ok(questionMapper.questionToQuestionDTO(
                 questionService.getQuestionById(questionId).orElseThrow()));
    }

    @PatchMapping(path = "/{questionId}/add/testFile" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addTestFileToQuestion(@RequestPart(value = "file") MultipartFile testFile,
                                                   @PathVariable("questionId") String questionId) {
        return ResponseEntity.ok(questionMapper.questionToQuestionDTO(questionService.addTestFile(questionId, testFile)));

    }



}
