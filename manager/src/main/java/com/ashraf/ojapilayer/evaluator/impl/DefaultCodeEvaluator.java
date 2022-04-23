package com.ashraf.ojapilayer.evaluator.impl;

import com.ashraf.ojapilayer.api.requestmodels.CodeEvaluationRequest;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.ashraf.ojapilayer.evaluator.CodeEvaluator;
import com.ashraf.ojapilayer.models.CodeExecutionResponse;
import com.ashraf.ojapilayer.util.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DefaultCodeEvaluator implements CodeEvaluator {

    @Override
    public CodeExecutionResponse evaluateCode(CodeEvaluationRequest request) {
        boolean isOutputSame = FileUtil.areFileContentsSame(new File(request.getCorrectOutputFilePath()),
                new File(request.getUserGeneratedOutputFilePath()));
        return CodeExecutionResponse.builder()
                .status(isOutputSame ? SubmissionStatus.ACCEPTED : SubmissionStatus.REJECTED)
                .build();
    }
}
