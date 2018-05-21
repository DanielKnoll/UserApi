package com.kulcs_soft.user_api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorHandler implements ErrorController {

    @Override
    public String getErrorPath(){
        return "/error";
    }

    @GetMapping(value = "/error")
    public String renderErrorPage(HttpServletResponse response, Model model) {
        return "";
    }
}
