package com.kulcs_soft.user_api;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("Get User by ID, VALID")
    public void testGetUserByIdValid() {
        assertEquals("TestName", memberService.getUserById(1L).getUserName());
    }

    @Test
    @DisplayName("Get User by ID, INVALID")
    public void testGetUserByIdInvalid() {
        assertThrows(EntityNotFoundException.class, () -> memberService.getUserById(100L));
        //assertNull(memberService.getUserById(100L));  // it does throws error
    }

    @Test
    @DisplayName("Test SaveUser")
    public void testSaveUser() {
        Member member = new Member("TestName3", "testEmail", "TestPassword");
        memberService.saveUser(member);
        assertEquals(3, memberService.getAllUser().size());
    }

    @Test
    @DisplayName("Test getAllUsers")
    public void testGetAllUsers() {
        assertEquals(3, memberService.getAllUser().size());
    }

    @Test
    @DisplayName("Get User by Name, VALID")
    public void testGetUserByName() {
        assertEquals("TestName2", memberService.getUserByName("TestName2").getUserName());
    }

    @Test
    @DisplayName("Get User by Name, INVALID")
    public void testGetUserByInvalidName() {
        assertNull(memberService.getUserByName("None").getUserName());
    }

    @Test
    @DisplayName("Check if name exists Name, TRUE")
    public void testIsUserNameFree() {
        assertTrue(memberService.isUserNameFree("TestName2"));
    }

    @Test
    @DisplayName("Check if name exists Name, FALSE")
    public void testIsUserNameFreeFalse() {
        assertFalse(memberService.isUserNameFree("Test"));
    }
}
