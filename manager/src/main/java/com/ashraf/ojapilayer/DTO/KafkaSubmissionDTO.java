package com.ashraf.ojapilayer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaSubmissionDTO {
    private Long submissionId;
    private String submissionDocumentLink;
    private String questionId;
}
