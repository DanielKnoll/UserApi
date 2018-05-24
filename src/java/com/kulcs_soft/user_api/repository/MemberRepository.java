package com.kulcs_soft.user_api.repository;

import com.kulcs_soft.user_api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserName(String userName);

    @Query("SELECT m.userId, m.userName, m.userEmail FROM Member m")
    List<Object> getUserIdAndAndUserNameAndUserEmail();
}
