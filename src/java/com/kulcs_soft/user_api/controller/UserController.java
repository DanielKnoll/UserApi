package com.kulcs_soft.user_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value = "/")
    public String renderMain(){
        return "index";
    }
}
