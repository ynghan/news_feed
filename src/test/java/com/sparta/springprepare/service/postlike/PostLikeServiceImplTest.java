package com.sparta.springprepare.service.postlike;

import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.repository.postlike.PostLikeRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostLikeServiceImplTest extends DummyObject {

    @InjectMocks
    PostLikeServiceImpl postLikeService;

    @Mock
    PostLikeRepository postLikeRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void onPostLike() {

        // given
        Long postId = 1L;
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        // stub
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit4572@naver.com");
        Post post = newMockPost(1L, "post contents 1", visit);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        PostLike postLike = newMockPostLike(1L, post, user);
        when(postLikeRepository.save(any())).thenReturn(postLike);

        // when
        PostLikeDto postLikeDto = postLikeService.onPostLike(postId, user);

        // then
        Assertions.assertThat(postLikeDto.getPostId()).isEqualTo(1L);
        Assertions.assertThat(postLikeDto.getUserId()).isEqualTo(1L);

    }

    @Test
    void deleteLikeToPost() {
        // given
        Long postId = 1L;
        User user1 = newMockUser(1L, "dudgks156", "dudgks56!!", "daum14572@naver.com");
        User user2 = newMockUser(2L, "dudgks256", "dudgks56!!", "daum24572@naver.com");
        User user3 = newMockUser(3L, "dudgks356", "dudgks56!!", "daum34572@naver.com");

        Post post = newMockPost(1L, "post contents 1", user1);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        PostLike postLike1 = newMockPostLike(1L, post, user1);
        PostLike postLike2 = newMockPostLike(2L, post, user2);
        PostLike postLike3 = newMockPostLike(3L, post, user3);
        List<PostLike> list = Arrays.asList(postLike1, postLike2, postLike3);
        PageImpl<PostLike> postLikes = new PageImpl<>(list);
        when(postLikeRepository.findByPostId(any(Long.class), any(Pageable.class))).thenReturn(postLikes);

        //when
        PostLikeDto postLikeDto = postLikeService.deleteLikeToPost(postId, user1, PageRequest.of(0, 3));

        //then
        Assertions.assertThat(postLikeDto.getUserId()).isEqualTo(1L);
        Assertions.assertThat(postLikeDto.getPostId()).isEqualTo(1L);
    }
}