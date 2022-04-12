package com.ashraf.ojapilayer.api.requestmodels;

import com.ashraf.ojapilayer.enums.ProgrammingLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequest {
    private String questionId;
    private String authorId;
    private ProgrammingLanguage language;
    private MultipartFile file;
}
