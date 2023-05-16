package com.lemonSoju.blog.repository;

import com.lemonSoju.blog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDataRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUid(String uid);
}