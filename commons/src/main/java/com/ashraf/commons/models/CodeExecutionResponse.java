package com.ashraf.commons.models;

import com.ashraf.commons.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeExecutionResponse {
    private SubmissionStatus status;
    private Duration executionTime;
    private Integer memory;
    private String error;
}
