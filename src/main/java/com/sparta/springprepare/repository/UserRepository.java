package com.sparta.springprepare.repository;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // Query Method
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Override
    List<User> findAll();

    // 특정 사용자를 팔로우한 다른 사용자들 조회
    @Query("select f from Follow f join fetch f.follower where f.followee.username = :username")
    List<Follow> findWithFolloweesByUsername(@Param("username") String username);

    // 특정 사용자가 팔로우한 다른 사용자들 조회
    @Query("select f from Follow f join fetch f.followee where f.follower.username = :username")
    List<Follow> findWithFollowersByUsername(@Param("username") String username);
}
