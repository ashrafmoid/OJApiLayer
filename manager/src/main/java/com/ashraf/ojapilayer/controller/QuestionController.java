package com.ashraf.ojapilayer.controller;

import com.ashraf.ojapilayer.DTO.QuestionDTO;
import com.ashraf.ojapilayer.api.requestmodels.AddQuestionRequest;
import com.ashraf.ojapilayer.api.requestmodels.FilterQueryRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addQuestion(@RequestPart(value = "file")MultipartFile file,
                                         @RequestPart(value = "metaData") QuestionMetaData metaData,
                                         @RequestPart(value = "testFile", required = false) MultipartFile testFile,
                                         @RequestPart(value = "outputFile", required = false) MultipartFile outputFile) {
        QuestionDTO questionDTO = questionMapper.questionToQuestionDTO(
                questionService.addQuestion(AddQuestionRequest.builder().questionFile(file)
                        .questionMetaData(metaData)
                        .testFile(testFile)
                        .outputFile(outputFile).build()));
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

    @PatchMapping(path = "/{questionId}/add/outputFile" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addOutputFileToQuestion(@RequestPart(value = "file") MultipartFile testFile,
                                                   @PathVariable("questionId") String questionId) {
        return ResponseEntity.ok(questionMapper.questionToQuestionDTO(questionService.addOutputFile(questionId, testFile)));

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuestions(
            @RequestParam(name = "page", defaultValue= "0") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "40")Integer size) {
        return ResponseEntity.ok(questionService.getAllQuestionsForPage(pageNumber, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getQuestionsByFilter(
            @RequestParam("query")String queryString,
            @RequestParam(value = "pageNumber" , defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "40") Integer size
    ) {
        FilterQueryRequest filterQueryRequest = FilterQueryRequest.builder().query(queryString)
                .pageNumber(pageNumber).size(size).build();
        return ResponseEntity.ok(questionService.getAllQuestionByFilter(filterQueryRequest));
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<?> updateQuestionMetaData(
            @RequestBody QuestionMetaData questionMetaData,
            @PathVariable("questionId") String questionId) {
        return ResponseEntity.ok(questionMapper.questionToQuestionDTO(
                questionService.updateQuestionMetaData(questionMetaData, questionId)));
    }

}
