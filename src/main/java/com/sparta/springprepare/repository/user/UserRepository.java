package com.sparta.springprepare.repository.user;


import com.sparta.springprepare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {

    List<User> findAll();
}
