package com.sparta.springprepare.repository.user.Impl;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.repository.user.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Optional;

import static com.sparta.springprepare.domain.QFollow.follow;
import static com.sparta.springprepare.domain.QUser.user;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

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
    public Page<Follow> findWithFolloweesByUsername(@Param("username") String username, Pageable pageable) {
        var query = jpaQueryFactory.selectFrom(follow)
                .innerJoin(follow.follower)
                .fetchJoin()
                .where(follow.followee.username.eq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        query.orderBy(follow.createdAt.desc());

        var follows = query.fetch();

        Long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(follow)
                .where(follow.followee.username.eq(username))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(follows, pageable, () -> totalSize);
    }

    @Override
    // 특정 사용자가 팔로우한 다른 사용자들 조회
//    @Query("select f from Follow f join fetch f.followee where f.follower.username = :username")
    public Page<Follow> findWithFollowersByUsername(@Param("username") String username, Pageable pageable) {
        var query = jpaQueryFactory
                .selectFrom(follow)
                .innerJoin(follow.followee)
                .fetchJoin()
                .where(follow.follower.username.eq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        query.orderBy(follow.createdAt.desc());
        var follows = query.fetch();
        Long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(follow)
                .where(follow.follower.username.eq(username))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(follows, pageable, () -> totalSize);

    }
}
