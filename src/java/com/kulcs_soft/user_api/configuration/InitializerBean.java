package com.kulcs_soft.user_api.configuration;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializerBean {
    private static boolean isTestRunning = false;

    @Autowired
    MemberService memberService;

    @PostConstruct
    public void init() {
        if(!isTestRunning) {
            Member member = new Member("Elek Teszt", "test@email.hu", "12345678");
            Member member2 = new Member("Marika Tesztelné", "testelne@freemail.hu", "abcd1234");
            Member member3 = new Member("Elek Érték", "ertek@aol.com", "&7]hLmX*:enW{pzw");
            Member member4 = new Member("Ian Groot", "groot@cgi.com", "IamGroot");
            Member member5 = new Member("Yoda", "lightside@force.sw", "MayThe4thBeWithYou");
            memberService.saveUser(member);
            memberService.saveUser(member2);
            memberService.saveUser(member3);
            memberService.saveUser(member4);
            memberService.saveUser(member5);
        }
    }

    public static void setTestRunning() {
        isTestRunning = true;
    }
}
