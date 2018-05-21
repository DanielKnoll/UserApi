package configuration;
import com.kulcs_soft.user_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializerBeanForTest {

    @Autowired
    MemberService memberService;
}
