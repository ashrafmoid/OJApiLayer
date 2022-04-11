package com.ashraf.ojapilayer.DTO;

import com.ashraf.ojapilayer.entity.UserProfile;
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
public class QuestionDTO {
    private String documentLink;
    private List<Topic> topics;
    private Integer rating;
    private List<UserProfileDTO> authors;
}
