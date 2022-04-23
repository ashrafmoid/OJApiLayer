package com.ashraf.ojapilayer.api.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeEvaluationRequest {
    private String userGeneratedOutputFilePath;
    private String correctOutputFilePath;
}
