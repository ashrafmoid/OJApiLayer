package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.LoginRequest;
import com.ashraf.ojapilayer.api.requestmodels.UserCreationRequest;

public interface UserRegistrationService {

    public void createUser(UserCreationRequest userCreationRequest);

    public void verifyLogin(LoginRequest loginRequest);
}
