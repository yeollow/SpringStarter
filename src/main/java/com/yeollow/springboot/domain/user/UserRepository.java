package com.yeollow.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//User의 CRUD를 책임 질 UserRepository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);               //소셜 로그인에서 email을 통해 이미 생성된 사용자인지 확인 여부를 위한 method.
}
