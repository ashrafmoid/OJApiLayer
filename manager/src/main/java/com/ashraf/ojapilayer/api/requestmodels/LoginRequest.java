package com.ashraf.ojapilayer.api.requestmodels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private final String id;
    private final String password;
}
