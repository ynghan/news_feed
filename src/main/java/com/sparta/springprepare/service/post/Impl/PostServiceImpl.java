package com.sparta.springprepare.service.post.Impl;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.handler.ex.CustomApiException;
import com.sparta.springprepare.handler.ex.ErrorCode;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import com.sparta.springprepare.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.springprepare.dto.postDto.PostRespDto.PostDetailRespDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 사용자 게시물 등록하기
    public PostRespDto createPost(PostReqDto requestDto, Long userId) {
        User findUser = checkUserById(userId);
        Post post = new Post();
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setUser(findUser);
        Post savePost = postRepository.save(post);
        return new PostRespDto(savePost);
    }

    // 특정 사용자 게시물 불러오기
    public Page<PostRespDto> getPosts(User user, Pageable pageable) {
        Page<Post> postList = postRepository.findAllByUser(user, pageable);
        return postList.map(PostRespDto::new);
    }

    // 특정 게시물 자세히 보기
    @Transactional(readOnly = true)
    public PostDetailRespDto getDetailPost(User loginUser, Long postId) {
        // 게시물 조회
        Post post = postRepository.findByIdWithUser(postId).orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_EXIST));

        // 게시물의 작성자가 로그인 사용자가 팔로우한 사용자이거나, 로그인 사용자 자신인 경우에만 접근 허용
        if (!post.getUser().getId().equals(loginUser.getId())) { // 게시물 작성자.eqauls(로그인 사용자) 가 아니라면
//            System.out.println("loginUserId : " + loginUser.getId());
            boolean isFollowing = post.getUser().getFollowees().stream()
                    .anyMatch(
                            follow -> follow.getFollower().getId()
                                    .equals(loginUser.getId()));
//            System.out.println("isFollowing" + isFollowing);
            if (!isFollowing) {
                throw new CustomApiException(ErrorCode.NOT_FOLLOWING_USER);
            }
        }

        // 댓글과 각 댓글의 좋아요 수 조회
        List<CommentRespDto> commentRespDtos = post.getComments().stream()
                .map(CommentRespDto::new)
                .toList();

        int postLikeCount = post.getPostLikes().size();

        return new PostDetailRespDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getUsername(),
                postLikeCount,
                commentRespDtos
        );
    }

    // 조회할 사용자가 로그인 사용자가 팔로우한 사용자 목록에 포함되어 있어야 사용자의 게시물을 조회할 수 있다.
    @Transactional(readOnly = true)
    @Cacheable(value = "post", key = "'user:' + #followUsername")
    public Page<PostRespDto> getUserPosts(String followUsername, User loginUser, Pageable pageable) {
        log.warn("사용자 게시물 조회, followUsername: {}", followUsername); // 로그 추가
        Long findUserId = checkUserByUsername(followUsername).getId();
        loginUser = checkUserById(loginUser.getId());
        Follow findFollowEntity = loginUser.getFollowers()
                .stream().filter(follow -> follow.getFollowee().getId().equals(findUserId))
                .findFirst().orElseThrow(() -> new CustomApiException(ErrorCode.NOT_FOLLOWING_USER));

        // 팔로우한 사용자의 모든 게시물 조회
        Page<Post> postList = postRepository.findAllByUser(findFollowEntity.getFollowee(), pageable);
        return postList.map(PostRespDto::new);
    }

    // 게시물 개수 불러오기
    public CountDto getPostCount(User user, Pageable pageable) {
        long count = postRepository.countByUser(user);
        return new CountDto(count);
    }

    // 로그인 사용자 특정 게시물 수정하기
    public PostRespDto updatePost(Long postId, PostReqDto requestDto, User user) {
        User findUser = checkUserByUsername(user.getUsername());
        Post findPost = checkPost(postId);
        if(findUser.getPosts().stream().anyMatch(post -> post.equals(findPost))) {
            findPost.setContent(requestDto.getContent());
            postRepository.save(findPost);
        }
        return new PostRespDto(findPost);
    }

    // 전체 게시물 조회
    public List<PostRespDto> getAllPosts() {
        List<Post> all = postRepository.findAll();

        List<PostRespDto> dtos = new ArrayList<>();

        for (Post post : all) {
            dtos.add(new PostRespDto(post));
        }
        return dtos;
    }

    // 특정 게시물 삭제
    public PostRespDto deletePost(Long postId) {
        Post deletePost = checkPost(postId);
        postRepository.deleteById(deletePost.getId());
        return new PostRespDto(deletePost);
    }

    // 특정 게시물 수정
    public PostRespDto updateAnyPost(PostReqDto dto, Long postId) {
        Post updatePost = checkPost(postId);
        updatePost.setContent(dto.getContent());
        Post savedPost = postRepository.save(updatePost);
        return new PostRespDto(savedPost);
    }


    private User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }

    private Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_EXIST));
    }
}
