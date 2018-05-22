package com.kulcs_soft.user_api.apiController;

import com.kulcs_soft.user_api.repository.MemberRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping(value = "/api/users")
    public ResponseEntity<JSONObject> getAlUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allUsers", memberRepository.getUserIdAndAndUserNameAndUserEmail());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
