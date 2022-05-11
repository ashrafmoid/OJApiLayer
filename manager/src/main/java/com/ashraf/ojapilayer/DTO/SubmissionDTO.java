package com.ashraf.ojapilayer.DTO;

import com.ashraf.ojapilayer.enums.ProgrammingLanguage;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {
    private SubmissionStatus status;
    private String questionId;
    private String authorId;
    private ProgrammingLanguage language;
    private Integer executionTime;
    private Integer memory;
    private String documentLink;
}
