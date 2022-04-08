package com.ashraf.ojapilayer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/home")
    public String getHomePage() {
        return "welcome";
    }
}
