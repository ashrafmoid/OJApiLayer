package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionMetaData {
    private List<String> topics;
    private Integer rating;
    private List<String> authorId;
    private String fileType;
}
