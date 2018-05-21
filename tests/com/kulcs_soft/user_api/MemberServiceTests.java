package com.kulcs_soft.user_api;

import com.kulcs_soft.user_api.service.MemberService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;
}
