package com.sparta.springprepare.repository;


import com.sparta.springprepare.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // Query Method
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
