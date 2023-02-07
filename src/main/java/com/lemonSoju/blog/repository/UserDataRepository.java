package com.lemonSoju.blog.repository;

import com.lemonSoju.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String uid);
}