package com.kulcs_soft.user_api.controller;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import com.kulcs_soft.user_api.utility.Password;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/")
    public String renderIndex(HttpSession session , Model model){
        if (session.getAttribute("userId") != null) {
            model.addAttribute("userName", session.getAttribute("userName"));
        }
        return "index";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletRequest req, HttpSession session, Model model) {
        String name = req.getParameter("userName");
        Member user = memberService.getUserByName(name);
        if(user != null &&
                Password.checkPassword(req.getParameter("userPassword"), user.getUserPassword())){
            session.setAttribute("userName", req.getParameter("userName"));
            session.setAttribute("userId", user.getUserId());
            return "redirect:/";
        }else{
            model.addAttribute("errormsg", "Login Failed! Username or Password invalid!");
            return "error";
        }
    }

    @PostMapping(value = "/registration")
    public String register(Model model, HttpServletRequest req, HttpSession session){
        if(memberService.isUserNameFree(req.getParameter("userName"))) {
            memberService.saveMember(
                    new Member(req.getParameter("userName"),
                            req.getParameter("userEmail"),
                            req.getParameter("userPassword")));
            Member member = memberService.getUserByName(req.getParameter("userName"));
            session.setAttribute("userName", req.getParameter("userName"));
            session.setAttribute("userId", member.getUserId());
            return "redirect:/";
        }else{
            model.addAttribute("error", "Registration Failed!");
            return "error";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
