package com.kulcs_soft.user_api.apiController;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import com.kulcs_soft.user_api.utility.Password;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/api/users")
    public ResponseEntity getAlUser(HttpSession session) {
        if (session.getAttribute("id") == null) {
            return new ResponseEntity<>("Login required", HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allUsers", memberService.getAllUserWithOutPswd());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/adduser", method = { RequestMethod.PUT, RequestMethod.POST })
    public ResponseEntity<String> addUserPut(@RequestBody Map<String, String> data, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return new ResponseEntity<>("Login required", HttpStatus.BAD_REQUEST);
        }
        return checkAndSaveUser(data);
    }

    @RequestMapping(value = "/api/deleteuser/{userId}", method = { RequestMethod.DELETE, RequestMethod.POST })
    public ResponseEntity<String> deleteUserDelete(@PathVariable("userId") String userIdSrting, HttpSession session) {
        if (session.getAttribute("id") == null) {
            return new ResponseEntity<>("Login required", HttpStatus.BAD_REQUEST);
        }
        return deleteUser(userIdSrting);
    }

    @PostMapping(value = "api/login")
    public ResponseEntity<String> loginJson(@RequestBody Map<String, String> data, HttpSession session) {
        String name = data.get("userName");
        Member user = memberService.getUserByName(name);
        if(user != null &&
                Password.checkPassword(data.get("userPassword"), user.getUserPassword())){
            session.setAttribute("userName", name);
            session.setAttribute("userId", user.getUserId());
            return new ResponseEntity<>("Logged in",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Login Failed! Username or Password invalid!",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "api/registration")
    public ResponseEntity<String> registerJson(@RequestBody Map<String, String> data, HttpSession session){
        ResponseEntity<String> responseEntity = checkAndSaveUser(data);

        if(responseEntity.getStatusCodeValue() == 400) {
            return responseEntity;
        }
        Member member = memberService.getUserByName(data.get("userName"));
        session.setAttribute("userName", member.getUserName());
        session.setAttribute("userId", member.getUserId());
        return new ResponseEntity<>("Registration successful",HttpStatus.OK);

    }

    @GetMapping(value = "api/logout")
    public ResponseEntity<String> logoutJson(HttpSession session){
        session.invalidate();
        return new ResponseEntity<>("User logged out.",HttpStatus.OK);
    }

    private ResponseEntity<String> checkAndSaveUser(Map<String, String> data) {
        String userName = data.get("userName");
        String userEmail = data.get("userEmail");
        String userPassword = data.get("userPassword");
        ResponseEntity<String> responseEntity = CheckUserInput(userName, userEmail, userPassword);

        if(responseEntity.getStatusCodeValue() == 400) {
            return responseEntity;
        }

        memberService.saveUser(new Member(userName, userEmail, userPassword));
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

    private ResponseEntity<String> deleteUser(String userIdSrting) {
        Long userId;
        try {
            userId = Long.valueOf(userIdSrting);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Argument is not a number", HttpStatus.BAD_REQUEST);
        }
        try {
            memberService.deleteUser(userId);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("There is no such user ID", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User with ID: " + userIdSrting + " deleted" , HttpStatus.OK);
    }
}
