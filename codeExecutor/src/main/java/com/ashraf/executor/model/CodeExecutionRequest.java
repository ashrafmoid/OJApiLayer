package com.ashraf.executor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeExecutionRequest {
    private String codeFilePath;
    private String testFilePath;
    private String errorFilePath;
    private String errorMsgFilePath;
    private Integer timeLimit;
}
