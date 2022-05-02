package com.ashraf.ojapilayer.api.requestmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterQueryRequest {
    private String query;
    private Integer pageNumber;
    private Integer size;
}
