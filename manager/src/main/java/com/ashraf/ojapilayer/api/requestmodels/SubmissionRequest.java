package com.ashraf.ojapilayer.api.requestmodels;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
public class SubmissionRequest extends SubmissionMetaData {
    private MultipartFile file;
}
