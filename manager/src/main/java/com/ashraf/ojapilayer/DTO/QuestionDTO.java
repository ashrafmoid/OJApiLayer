package com.ashraf.ojapilayer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private List<String> topics;
    private Integer rating;
    private List<UserProfileDTO> authors;
    private String questionName;
    private String questionFileLink;
}
