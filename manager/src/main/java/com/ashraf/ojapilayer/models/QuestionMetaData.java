package com.ashraf.ojapilayer.models;

import com.ashraf.ojapilayer.enums.Topic;
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
    private List<Topic> topics;
    private Integer rating;
    private List<String> authorIds;
    private String fileType;
    private String questionName;
}
