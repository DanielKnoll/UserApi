package com.kulcs_soft.user_api.service;

import com.kulcs_soft.user_api.model.Member;
import com.kulcs_soft.user_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public Member getUserById(Long userId) throws EntityNotFoundException {
        return memberRepository.getOne(userId);
    }

    public List<Member> getAllUser() {
        return memberRepository.findAll();
    }

    public Member getUserByName(String name) {
        return memberRepository.findByUserName(name);
    }

    public boolean isUserNameFree(String name) {
        return (getUserByName(name)== null);
    }
}
