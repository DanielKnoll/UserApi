package com.kulcs_soft.user_api.service;

import com.kulcs_soft.user_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
}
