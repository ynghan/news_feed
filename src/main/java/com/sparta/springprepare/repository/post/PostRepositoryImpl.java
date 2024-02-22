package com.sparta.springprepare.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.springprepare.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAllByUser(User user) {
        return jpaQueryFactory.selectFrom(post).where(post.user.eq(user)).fetch();
    }
}
