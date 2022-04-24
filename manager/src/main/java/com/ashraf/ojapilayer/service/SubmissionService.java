package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.SubmissionRequest;
import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.models.CodeExecutionResponse;

import java.util.Optional;

public interface SubmissionService {
    Submission submitSolution(SubmissionRequest request);
    Optional<Submission> getSubmissionById(String id);
    void updateSubmissionResult(CodeExecutionResponse response, Long submissionId);
    void saveSubmission(Submission submission);
}
