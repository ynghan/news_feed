package com.sparta.springprepare.repository.commentlike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import com.sparta.springprepare.domain.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sparta.springprepare.domain.QCommentLike.commentLike;

@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentLike> findByComment(Comment comment, Pageable pageable) {

        var query = jpaQueryFactory
                .selectFrom(commentLike)
                .innerJoin(QComment.comment)
                .fetchJoin()
                .where(commentLike.comment.eq(comment))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        var commentLikes = query.fetch();
        Long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(commentLike)
                .where(commentLike.comment.eq(comment))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(commentLikes, pageable, () -> totalSize);
    }
}
