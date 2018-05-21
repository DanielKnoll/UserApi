package com.kulcs_soft.user_api;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("Test SaveMember")
    public void testSaveMember() {
        Member member = new Member("TestName", "testEmail", "TestPassword");
        memberService.saveMember(member);
        assertEquals("TestName", memberService.getMemberById(Long.valueOf(1)).getUserName());
    }
}
