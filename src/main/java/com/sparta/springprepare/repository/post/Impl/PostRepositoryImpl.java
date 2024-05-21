package com.sparta.springprepare.repository.post.Impl;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.repository.post.PostRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sparta.springprepare.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> findAllByUser(User user, Pageable pageable) {
        var limit = jpaQueryFactory.selectFrom(post)
                .where(post.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        limit.orderBy(post.createdAt.asc());

        var posts = limit.fetch();

        long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(post)
                .where(post.user.eq(user))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(posts, pageable, () -> totalSize);
    }

    @Override
    public long countByUser(User user) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(post)
                .where(post.user.eq(user))
                .fetch().get(0);
    }

}
