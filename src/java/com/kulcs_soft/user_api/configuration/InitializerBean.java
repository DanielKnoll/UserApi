package com.kulcs_soft.user_api.configuration;

import com.kulcs_soft.user_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    @Autowired
    MemberService memberService;
}
