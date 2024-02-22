package com.sparta.springprepare.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class UserTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("사용자 생성(이름, 비밀번호, 이메일, 권한) 생성자")
    void test1() {
        String username = "sdjafl";
        String password = "sdajfl";
        String email = "jsaflk";
        UserRoleEnum role = UserRoleEnum.USER;

//        User user = new User(username, password, email, role);

//        em.persist(user);
    }
}

