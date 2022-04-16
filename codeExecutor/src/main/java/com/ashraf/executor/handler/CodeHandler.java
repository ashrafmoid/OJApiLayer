package com.ashraf.executor.handler;


import com.ashraf.commons.models.CodeExecutionRequest;
import com.ashraf.commons.models.CodeExecutionResponse;

public interface CodeHandler {
    CodeExecutionResponse executeCode(CodeExecutionRequest request);
}
