package com.ashraf.ojapilayer.api.requestmodels;

import com.ashraf.ojapilayer.enums.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionMetaData {
    private String questionId;
    private String authorId;
    private ProgrammingLanguage language;
}
