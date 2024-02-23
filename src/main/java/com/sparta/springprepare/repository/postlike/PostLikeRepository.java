package com.sparta.springprepare.repository.postlike;


import com.sparta.springprepare.domain.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

    // 게시물 entity나 id로 연관된 모든 postLike 엔티티 찾기
    @Override
    Page<PostLike> findByPostId(Long postId, Pageable pageable);
}
