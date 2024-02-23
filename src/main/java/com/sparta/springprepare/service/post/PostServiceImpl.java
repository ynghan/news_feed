package com.sparta.springprepare.service.post;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.util.EntityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final EntityCheckUtil entityCheckUtil;


    // 사용자 게시물 등록하기
    public PostRespDto createPost(PostReqDto requestDto, Long userId) {
        User findUser = entityCheckUtil.checkUserById(userId);
        Post post = new Post();
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

    // 조회할 사용자가 로그인 사용자가 팔로우한 사용자 목록에 포함되어 있어야 사용자의 게시물을 조회할 수 있다.
    @Transactional
    public Page<PostRespDto> getUserPosts(String followUsername, User loginUser, Pageable pageable) {
        Long findUserId = entityCheckUtil.checkUserByUsername(followUsername).getId();
        loginUser = entityCheckUtil.checkUserById(loginUser.getId());

        Follow findFollowEntity = loginUser.getFollowers()
                .stream().filter(follow -> follow.getFollowee().getId().equals(findUserId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 사용자는 팔로우한 사용자가 아닙니다."));

        Page<Post> postList = postRepository.findAllByUser(findFollowEntity.getFollowee(), pageable);
        return postList.map(PostRespDto::new);
    }

    // 게시물 개수 불러오기
    public CountDto getPostCount(User user, Pageable pageable) {
        Page<Post> postList = postRepository.findAllByUser(user, pageable);

        long count = postList.getSize();

        return new CountDto(count);
    }

    // 특정 게시물 수정하기
    public PostRespDto updatePost(Long postId, PostReqDto requestDto, User user) {
        User findUser = entityCheckUtil.checkUserByUsername(user.getUsername());
        Post findPost = entityCheckUtil.checkPost(postId);
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
        Post deletePost = entityCheckUtil.checkPost(postId);
        postRepository.delete(deletePost);
        return new PostRespDto(deletePost);
    }

    // 특정 게시물 수정
    public PostRespDto updatePost(PostReqDto dto, Long postId) {
        Post updatePost = entityCheckUtil.checkPost(postId);
        updatePost.setContent(dto.getContent());
        Post savedPost = postRepository.save(updatePost);
        return new PostRespDto(savedPost);
    }
}
