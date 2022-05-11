package com.ashraf.ojapilayer.evaluator.impl;

import com.ashraf.ojapilayer.api.requestmodels.CodeEvaluationRequest;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.ashraf.ojapilayer.evaluator.CodeEvaluator;
import com.ashraf.ojapilayer.models.CodeExecutionResponse;
import com.ashraf.ojapilayer.util.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

@Component
public class DefaultCodeEvaluator implements CodeEvaluator {

    @Override
    public CodeExecutionResponse evaluateCode(CodeEvaluationRequest request) throws IOException {
        String errorText = FileUtil.getFileAsString(request.getErrorFilePath());
        String errorMsg = FileUtil.getFileAsString(request.getErrorMsgFilePath());
        if (errorText.isEmpty()) {
            boolean isOutputSame = FileUtil.areFileContentsSame(new File(request.getCorrectOutputFilePath()),
                    new File(request.getUserGeneratedOutputFilePath()));
            Integer highestExecutionTime = Arrays.stream(FileUtil.getFileAsString(request.getReportingFilePath()).split("\n"))
                    .map(Integer::valueOf).mapToInt(v -> v).max().orElseThrow();
            return CodeExecutionResponse.builder()
                    .status(isOutputSame ? SubmissionStatus.ACCEPTED : SubmissionStatus.REJECTED)
                    .executionTimeInMillis(highestExecutionTime)
                    .build();
        }
        return CodeExecutionResponse.builder()
                .status(SubmissionStatus.valueOf(errorText))
                .error(errorMsg.isEmpty() ? null : errorMsg)
                .build();
    }
}
