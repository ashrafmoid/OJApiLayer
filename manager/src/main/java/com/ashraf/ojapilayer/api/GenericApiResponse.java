package com.ashraf.ojapilayer.api;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class GenericApiResponse {
    Object data;
    Map<String, Object> metaData;
}
