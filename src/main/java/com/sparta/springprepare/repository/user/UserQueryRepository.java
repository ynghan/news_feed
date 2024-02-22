package com.sparta.springprepare.repository.user;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {

    // Query Method
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    // 특정 사용자를 팔로우한 다른 사용자들 조회
    List<Follow> findWithFolloweesByUsername(@Param("username") String username);

    // 특정 사용자가 팔로우한 다른 사용자들 조회
    List<Follow> findWithFollowersByUsername(@Param("username") String username);
}
