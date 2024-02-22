package com.sparta.springprepare.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Comment;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sparta.springprepare.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Comment> findById(Long id) {
        Comment commentPS = jpaQueryFactory.selectFrom(comment).where(comment.id.eq(id)).fetchOne();
        return Optional.ofNullable(commentPS);
    }

}
