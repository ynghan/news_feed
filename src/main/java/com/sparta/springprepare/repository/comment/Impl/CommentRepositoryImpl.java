package com.sparta.springprepare.repository.comment.Impl;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.repository.comment.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Optional;

import static com.sparta.springprepare.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Comment> findById(Long id) {
        Comment commentPS = jpaQueryFactory.selectFrom(comment)
                .where(comment.id.eq(id)).fetchOne();
        return Optional.ofNullable(commentPS);
    }

    @Override
    public Page<Comment> findByPost(Post post, Pageable pageable) {
        var limit = jpaQueryFactory.selectFrom(comment)
                .where(comment.post.eq(post))
                .offset(0)
                .limit(10);

        limit.orderBy(comment.createdAt.asc());

        var comments = limit.fetch();

        Long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(comment)
                .where(comment.post.eq(post))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(comments, pageable, () -> totalSize);
    }
}
