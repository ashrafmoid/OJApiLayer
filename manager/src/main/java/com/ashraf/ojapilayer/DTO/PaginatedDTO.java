package com.ashraf.ojapilayer.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedDTO <T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer size;
    private Integer totalElements;
    private Integer totalPages;
    @JsonProperty("isLast")
    private boolean isLast;
}
