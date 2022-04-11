package com.ashraf.ojapilayer.api.requestmodels;

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
public class Tags {
    List<Topic> topics;
    Integer rating;
    List<String> authorNames;
}
