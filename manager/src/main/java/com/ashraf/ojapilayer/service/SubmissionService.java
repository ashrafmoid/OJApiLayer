package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.SubmissionRequest;
import com.ashraf.ojapilayer.entity.Submission;

import java.util.Optional;

public interface SubmissionService {
    String submitSolution(SubmissionRequest request);
    Optional<Submission> getSubmissionById(String id);
}
