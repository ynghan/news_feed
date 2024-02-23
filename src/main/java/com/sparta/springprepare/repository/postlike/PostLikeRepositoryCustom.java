package com.sparta.springprepare.repository.postlike;

import com.sparta.springprepare.domain.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostLikeRepositoryCustom {

    Page<PostLike> findByPostId(Long postId, Pageable pageable);
}
