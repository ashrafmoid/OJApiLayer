package com.ashraf.ojapilayer.controller;

import com.ashraf.ojapilayer.api.requestmodels.LoginRequest;
import com.ashraf.ojapilayer.api.requestmodels.UserCreationRequest;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final UserManagementService userRegistrationService;

    @RequestMapping("/home")
    public String getHomePage() {
        return "welcome";
    }

    @RequestMapping(value = "/home/register", method = RequestMethod.POST)
    public String userRegister(final @RequestBody UserCreationRequest userCreationRequest) {
        userRegistrationService.createUser(userCreationRequest);
        return "logged-in-page";
    }

    @RequestMapping(value = "/home/login", method = RequestMethod.POST)
    public String userLogin(final @RequestBody LoginRequest loginRequest) {
        userRegistrationService.verifyLogin(loginRequest);
        return "logged-in-page";
    }
}
