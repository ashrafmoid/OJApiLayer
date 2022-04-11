package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.LoginRequest;
import com.ashraf.ojapilayer.api.requestmodels.UserCreationRequest;
import com.ashraf.ojapilayer.entity.UserProfile;

import java.util.List;

public interface UserManagementService {

    void createUser(UserCreationRequest userCreationRequest);

    void verifyLogin(LoginRequest loginRequest);

    List<UserProfile> findAllUserById(List<String> ids);
}
