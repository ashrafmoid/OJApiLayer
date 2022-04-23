package com.ashraf.ojapilayer.evaluator;

import com.ashraf.ojapilayer.api.requestmodels.CodeEvaluationRequest;
import com.ashraf.ojapilayer.models.CodeExecutionResponse;

public interface CodeEvaluator {
    CodeExecutionResponse evaluateCode(CodeEvaluationRequest request);
}
