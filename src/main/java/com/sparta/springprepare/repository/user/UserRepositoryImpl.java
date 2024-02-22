package com.sparta.springprepare.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.sparta.springprepare.domain.QFollow.follow;
import static com.sparta.springprepare.domain.QUser.user;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        User userPS = jpaQueryFactory.selectFrom(user).where(user.username.eq(username)).fetchOne();

        return Optional.ofNullable(userPS);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        User userPS = jpaQueryFactory.selectFrom(user).where(user.email.eq(email)).fetchOne();
        return Optional.ofNullable(userPS);
    }

    @Override
    // 특정 사용자를 팔로우한 다른 사용자들 조회
    public List<Follow> findWithFolloweesByUsername(@Param("username") String username) {
        return jpaQueryFactory.selectFrom(follow).innerJoin(follow.follower).fetchJoin().where(follow.followee.username.eq(username)).fetch();
    }

    @Override
    // 특정 사용자가 팔로우한 다른 사용자들 조회
//    @Query("select f from Follow f join fetch f.followee where f.follower.username = :username")
    public List<Follow> findWithFollowersByUsername(@Param("username") String username) {
        return jpaQueryFactory.selectFrom(follow).innerJoin(follow.followee).fetchJoin().where(follow.follower.username.eq(username)).fetch();
    }
}
