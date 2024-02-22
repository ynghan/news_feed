package com.sparta.springprepare.repository.user;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepositoryCustom {

    // Query Method
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // 특정 사용자를 팔로우한 다른 사용자들 조회
    Page<Follow> findWithFolloweesByUsername(@Param("username") String username, Pageable pageable);

    // 특정 사용자가 팔로우한 다른 사용자들 조회
    Page<Follow> findWithFollowersByUsername(@Param("username") String username, Pageable pageable);
}
