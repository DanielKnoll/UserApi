package configuration;
import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializerBeanForTest {

    @Autowired
    MemberService memberService;

    @PostConstruct
    public void init() {
        Member member = new Member("TestName", "testEmail", "TestPassword");
        Member member2 = new Member("TestName2", "testEmail", "TestPassword");
        memberService.saveMember(member);
        memberService.saveMember(member2);
    }
}
