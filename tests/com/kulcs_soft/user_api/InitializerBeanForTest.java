package com.kulcs_soft.user_api;
import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.kulcs_soft.user_api.configuration.InitializerBean.setTestRunning;

@Component
public class InitializerBeanForTest {

    @Autowired
    private MemberService memberService;

    @PostConstruct
    public void init() {
        setTestRunning();
        Member member = new Member("TestName", "testEmail", "TestPassword");
        Member member2 = new Member("TestName2", "testEmail2", "TestPassword2");
        memberService.saveUser(member);
        memberService.saveUser(member2);
    }
}
