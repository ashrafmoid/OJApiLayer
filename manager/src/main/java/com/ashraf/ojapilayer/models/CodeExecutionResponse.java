package com.ashraf.ojapilayer.models;

import com.ashraf.ojapilayer.enums.SubmissionStatus;
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
    private Integer executionTimeInMillis;
    private Integer memory;
    private String error;
}
