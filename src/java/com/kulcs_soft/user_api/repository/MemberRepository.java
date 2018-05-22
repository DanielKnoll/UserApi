package com.kulcs_soft.user_api.repository;

import com.kulcs_soft.user_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserName(String userName);
}
