package com.kulcs_soft.user_api.configuration;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import com.kulcs_soft.user_api.utility.Password;
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
            memberService.saveMember(member);
            memberService.saveMember(member2);
            memberService.saveMember(member3);
            memberService.saveMember(member4);
            memberService.saveMember(member5);
        }
    }

    public static void setTestRunning() {
        isTestRunning = true;
    }
}
