package com.sparta.springprepare.repository.postlike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import static com.sparta.springprepare.domain.QPostLike.postLike;

@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostLike> findByPostId(Long postId, Pageable pageable) {
        // postId인 post entity를 찾고, 해당 entity에 대한 postLike 리스트 찾기
        var postLikes = jpaQueryFactory
                .selectFrom(postLike)
                .where(postLike.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        Long totalSize = jpaQueryFactory.select(Wildcard.count)
                .from(postLike)
                .where(postLike.post.id.eq(postId))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(postLikes, pageable, () -> totalSize);

    }
}
