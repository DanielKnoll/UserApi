package com.kulcs_soft.user_api.apiController;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.repository.MemberRepository;
import com.kulcs_soft.user_api.service.MemberService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserApiController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping(value = "/api/users")
    public ResponseEntity<JSONObject> getAlUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allUsers", memberRepository.getUserIdAndAndUserNameAndUserEmail());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @PutMapping(value = "/api/adduser")
    public ResponseEntity<String> addUser(@RequestBody Map<String, Object> data) {
        String userName = data.get("userName").toString();
        String userEmail = data.get("userEmail").toString();
        String userPassword = data.get("userPassword").toString();
        ResponseEntity<String> responseEntity = CheckUserInput(userName, userEmail, userPassword);

        if(responseEntity.getStatusCodeValue() == 400) {
            return responseEntity;
        }

        memberService.saveMember(new Member(userName, userEmail, userPassword));
        return responseEntity;
    }

    /**
     * Validating user inputs name by length and if taken, email with regex, password just by length for now.
     * @param name: String, the input from user for username
     * @param email: String, the input from user for email address
     * @param pswd:String, the input from user for password
     * @return ResponseEntity\<String>: HttpStatus.BAD_REQUEST or HttpStatus.OK with message
     */
    private ResponseEntity<String> CheckUserInput(String name, String email, String pswd) {
        int userNameCharLimit = 3;
        int userPswdCharLimit = 8;
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if (name.length() < userNameCharLimit) {
            return new ResponseEntity<>("Username is too short", HttpStatus.BAD_REQUEST);
        } else if (!memberService.isUserNameFree(name)) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        if(!mat.matches()) {
            return new ResponseEntity<>("Email address is not valid", HttpStatus.BAD_REQUEST);
        }
        if (pswd.length() < userPswdCharLimit) {
            return new ResponseEntity<>("Password is too short", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User added.",HttpStatus.OK);
    }
}
