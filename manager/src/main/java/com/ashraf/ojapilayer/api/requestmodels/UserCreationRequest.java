package com.ashraf.ojapilayer.api.requestmodels;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreationRequest {
    String id;
    String password;
}
