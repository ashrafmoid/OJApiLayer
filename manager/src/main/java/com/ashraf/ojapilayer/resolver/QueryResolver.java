package com.ashraf.ojapilayer.resolver;

import com.ashraf.ojapilayer.DTO.PaginatedDTO;
import org.springframework.data.domain.Pageable;

public interface QueryResolver {
    <T> PaginatedDTO<T> getResultsForQuery(String queryString, Class<T> clazz, Pageable pageable);
}
