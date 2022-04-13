package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.entity.Submission;
import com.ashraf.ojapilayer.service.EvaluationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EvaluationServiceImpl implements EvaluationService {

    @Override
    public void evaluateSubmission(Submission submission) {
     log.info("Evaluating Submission {}", submission);
    }
}
