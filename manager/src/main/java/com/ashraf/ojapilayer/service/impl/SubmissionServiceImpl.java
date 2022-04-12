package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.api.requestmodels.SubmissionRequest;
import com.ashraf.ojapilayer.constants.Constant;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.ashraf.ojapilayer.repository.SubmissionRepository;
import com.ashraf.ojapilayer.service.DocumentService;
import com.ashraf.ojapilayer.service.QuestionService;
import com.ashraf.ojapilayer.service.SubmissionService;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final DocumentService documentService;
    private final SubmissionRepository submissionRepository;
    private final UserManagementService userManagementService;
    private final QuestionService questionService;

   /*
      1.upload solution to DB
      2.trigger workflow for evaluation(Async)
    */

    @Override
    public String submitSolution(SubmissionRequest request) {
        String fileId = documentService.uploadMultipartFile(FileUploadRequest.builder()
                .multipartFile(request.getFile())
                .fileType(Constant.TEXT_PLAIN_APPLICATION_TYPE.toString())
                .build());
        UserProfile userProfile = userManagementService.findUserById(Long.valueOf(request.getAuthorId()));
        Question question = questionService.getQuestionById(Long.valueOf(request.getQuestionId()));
        Submission submission = Submission.builder().documentLink(fileId)
                .language(request.getLanguage())
                .author(userProfile)
                .question(question)
                .status(SubmissionStatus.QUEUED)
                .build();
        submission = submissionRepository.save(submission);
        // trigger workflow here
        return submission.getDocumentLink();

    }

    @Override
    public Optional<Submission> getSubmissionById(String id) {
        return submissionRepository.findById(Long.valueOf(id));
    }
}
