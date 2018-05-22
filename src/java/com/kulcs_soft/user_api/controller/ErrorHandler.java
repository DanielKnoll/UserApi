package com.kulcs_soft.user_api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@Controller
public class ErrorHandler implements ErrorController {

    @Override
    public String getErrorPath(){
        return "/error";
    }

    @RequestMapping(value = "/error")
    public ModelAndView renderErrorPage(HttpServletResponse response, Model model){

        String errorMsg = "";
        int statusCode = getErrorCode(response);

        switch (statusCode) {
            case 400:
                errorMsg = "Error 400: Bad Request";
                break;
            case 401:
                errorMsg = "Error 401: Unauthorized";
                break;
            case 403:
                errorMsg = "Error 403: Forbidden";
                break;
            case 404:
                errorMsg = "Error 404: Resource not found";
                break;
            case 500:
                errorMsg = "Error 500: Internal Server Error";
                break;
            default:
                errorMsg = "Error " + statusCode + ": Come back later";
        }
        model.addAttribute("errormsg", errorMsg);
        return new ModelAndView("error");
    }

    private int getErrorCode(HttpServletResponse response) {
        return response.getStatus();
    }
}
