package com.sparta.springprepare.repository.user;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Override
    List<User> findAll();

    @Override
    Optional<User> findByUsername(String username);

    @Override
    Optional<User> findByEmail(String email);

    @Override
    Page<Follow> findWithFolloweesByUsername(@Param("username") String username, Pageable pageable);

    @Override
    Page<Follow> findWithFollowersByUsername(@Param("username") String username, Pageable pageable);
}
