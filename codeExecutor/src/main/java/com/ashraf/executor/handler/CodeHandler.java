package com.ashraf.executor.handler;

import com.ashraf.executor.model.CodeExecutionRequest;
import com.ashraf.executor.model.CodeExecutionResponse;

public interface CodeHandler {
    CodeExecutionResponse executeCode(CodeExecutionRequest request);
}
