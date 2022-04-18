package com.ashraf.ojapilayer.api.requestmodels;

import com.ashraf.ojapilayer.models.QuestionMetaData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddQuestionRequest {
    private MultipartFile questionFile;
    private MultipartFile testFile;
    private QuestionMetaData questionMetaData;
}
