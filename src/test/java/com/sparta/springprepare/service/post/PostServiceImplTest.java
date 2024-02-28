package com.sparta.springprepare.service.post;

import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest extends DummyObject {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Test
    void createPost_test() throws Exception {
        //given
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setContent("");

        Long userId = 1L;

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Post post = newMockPost(2L, "education contents", user);
        when(postRepository.save(any())).thenReturn(post);

        //when
        PostRespDto postRespDto = postService.createPost(postReqDto, userId);

        //then
        assertThat(postRespDto.getPostId()).isEqualTo(2L);
        assertThat(postRespDto.getUserId()).isEqualTo(1L);
        assertThat(postRespDto.getContent()).isEqualTo("education contents");
    }

    @Test
    void getPosts_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        Post postA = newMockPost(1L, "AAA", user);
        Post postB = newMockPost(2L, "BBB", user);

        List<Post> posts = Arrays.asList(postA, postB);
        Page<Post> postPage = new PageImpl<>(posts);

        // stub
        when(postRepository.findAllByUser(any(User.class), any(Pageable.class))).thenReturn(postPage);

        //when
        Page<PostRespDto> postRespDtoPage = postService.getPosts(user, PageRequest.of(0, 2));

        //then
        assertThat(2).isEqualTo(postRespDtoPage.getNumberOfElements());
        assertThat(1L).isEqualTo(postRespDtoPage.getContent().get(0).getUserId());
        assertThat(1L).isEqualTo(postRespDtoPage.getContent().get(1).getUserId());
        assertThat("AAA").isEqualTo(postRespDtoPage.getContent().get(0).getContent());
        assertThat("BBB").isEqualTo(postRespDtoPage.getContent().get(1).getContent());
        assertThat(1L).isEqualTo(postRespDtoPage.getContent().get(0).getPostId());
        assertThat(2L).isEqualTo(postRespDtoPage.getContent().get(1).getPostId());

    }

    @Test
    void getUserPosts_test() throws Exception {

        //given
        String followUsername = "userA";
        User loginUser = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");


        User userA = newMockUser(2L, "userA", "user11!!", "userA@aaa.aaa");
        User userB = newEmptyUser(3L, "userB");

        // loginUser의 List<Follow> 엔티티 set
        List<Follow> follows = Arrays.asList(
                newMockFollow(1L, loginUser, userA),
                newMockFollow(2L, loginUser, userB));
        loginUser.setFollowers(follows);

        // userA의 List<Post> 엔티티 set
        Post post1 = newMockPost(1L, "content1", userA);
        Post post2 = newMockPost(2L, "content2", userA);
        Post post3 = newMockPost(3L, "content3", userA);
        List<Post> posts = Arrays.asList(post1, post2, post3);
        userA.setPosts(posts);

        PageImpl<Post> postPage = new PageImpl<>(posts);

        //when
        when(userRepository.findByUsername(followUsername)).thenReturn(Optional.of(userA));

        when(userRepository.findById(loginUser.getId())).thenReturn(Optional.of(loginUser));

        when(postRepository.findAllByUser(any(User.class), any(Pageable.class))).thenReturn(postPage);

        Page<PostRespDto> respDtos = postService.getUserPosts(followUsername, loginUser, PageRequest.of(0, 3));

        //then
        assertThat(respDtos.getContent().get(0).getUserId()).isEqualTo(2L);
        assertThat(respDtos.getContent().get(0).getPostId()).isEqualTo(1L);
        assertThat(respDtos.getContent().get(0).getContent()).isEqualTo("content1");

        assertThat(respDtos.getContent().get(1).getUserId()).isEqualTo(2L);
        assertThat(respDtos.getContent().get(1).getPostId()).isEqualTo(2L);
        assertThat(respDtos.getContent().get(1).getContent()).isEqualTo("content2");

        assertThat(respDtos.getContent().get(2).getUserId()).isEqualTo(2L);
        assertThat(respDtos.getContent().get(2).getPostId()).isEqualTo(3L);
        assertThat(respDtos.getContent().get(2).getContent()).isEqualTo("content3");
    }

    @Test
    void getPostCount_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        // stub
        Post post1 = newMockPost(1L, "content1", user);
        Post post2 = newMockPost(2L, "content2", user);
        Post post3 = newMockPost(3L, "content3", user);
        List<Post> posts = Arrays.asList(post1, post2, post3);
        PageImpl<Post> postPage = new PageImpl<>(posts);
        when(postRepository.findAllByUser(any(User.class), any(Pageable.class))).thenReturn(postPage);

        //when
        CountDto postCount = postService.getPostCount(user, PageRequest.of(0, 3));

        //then
        assertThat(3).isEqualTo(postCount.getCount());
    }

    @Test
    void updatePost_test() throws Exception {
        //given
        Long postId = 1L;

        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setContent("fixed contents");

        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        //when
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        Post postA = newMockPost(1L, "basic1 contents", user);
        Post postB = newMockPost(2L, "basic2 contents", user);
        Post postC = newMockPost(3L, "basic3 contents", user);
        when(postRepository.findById(any())).thenReturn(Optional.of(postB));

        List<Post> posts = Arrays.asList(postA, postB, postC);
        user.setPosts(posts);

        PostRespDto postRespDto = postService.updatePost(2L, postReqDto, user);
        //then
        assertThat(1L).isEqualTo(postRespDto.getUserId());
        assertThat("fixed contents").isEqualTo(postRespDto.getContent());
        assertThat(2L).isEqualTo(postRespDto.getPostId());

    }

    @Test
    void getAllPosts_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        List<Post> posts = Arrays.asList(
                newMockPost(1L, "contents1", user),
                newMockPost(2L, "contents2", user),
                newMockPost(3L, "contents3", user)
        );
        when(postRepository.findAll()).thenReturn(posts);
        //when
        List<PostRespDto> allPosts = postService.getAllPosts();
        //then
        assertThat(allPosts.get(0).getPostId()).isEqualTo(1L);
    }

    @Test
    void deletePost_test() throws Exception {
        //given
        Long postId = 1L;
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        Post post = newMockPost(1L, "contents1", user);

        // stub
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        //when
        PostRespDto postRespDto = postService.deletePost(postId);

        //then
        assertThat(postRespDto.getPostId()).isEqualTo(1L);
        assertThat(postRespDto.getContent()).isEqualTo("contents1");
    }

    @Test
    void updateAnyPost_test() throws Exception {
        //given
        Long postId = 1L;
        PostReqDto postReqDto = new PostReqDto();
        postReqDto.setContent("fixed contents");

        // stub
        User user = newEmptyUser(1L, "dudgks");
        Post post = newMockPost(1L, "origin contents", user);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        Post fixedPost = newMockPost(1L, "fixed contents", user);
        when(postRepository.save(any())).thenReturn(fixedPost);

        //when
        PostRespDto postRespDto = postService.updateAnyPost(postReqDto, postId);

        //then
        assertThat(postRespDto.getUserId()).isEqualTo(1L);
        assertThat(postRespDto.getPostId()).isEqualTo(1L);
        assertThat(postRespDto.getContent()).isEqualTo("fixed contents");
    }
}