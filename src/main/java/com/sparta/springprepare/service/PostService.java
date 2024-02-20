package com.sparta.springprepare.service;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.repository.PostRepository;
import com.sparta.springprepare.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 사용자 게시물 등록하기
    public PostRespDto createPost(PostReqDto requestDto, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = new Post();
        post.setContent(requestDto.getContent());
        post.setUser(findUser);
        Post savePost = postRepository.save(post);
        return new PostRespDto(savePost);
    }

    // 특정 사용자 게시물 불러오기
    public List<PostRespDto> getPosts(User user) {
        List<Post> postList = postRepository.findAllByUser(user);
        List<PostRespDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {
            responseDtoList.add(new PostRespDto(post));
        }
        return responseDtoList;
    }

    // 조회할 사용자가 로그인 사용자가 팔로우한 사용자 목록에 포함되어 있어야 사용자의 게시물을 조회할 수 있다.
    @Transactional
    public List<PostRespDto> getUserPosts(String followUsername, User loginUser) {
        Long findUserId = userRepository.findByUsername(followUsername).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")).getId();
        loginUser = userRepository.findById(loginUser.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Follow findFollowEntity = loginUser.getFollowers()
                .stream().filter(follow -> follow.getFollowee().getId().equals(findUserId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 사용자는 팔로우한 사용자가 아닙니다."));

        List<Post> postList = postRepository.findAllByUser(findFollowEntity.getFollowee());
        ArrayList<PostRespDto> responseDtoList = new ArrayList<>();
        for (Post post : postList) {
            responseDtoList.add(new PostRespDto(post));
        }
        return responseDtoList;
    }

    // 게시물 개수 불러오기
    public CountDto getPostCount(User user) {
        List<Post> postList = postRepository.findAllByUser(user);

        long count = postList.size();

        return new CountDto(count);
    }

    // 특정 게시물 수정하기
    public PostRespDto updatePost(Long postId, PostReqDto requestDto, User user) {
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하는 사용자가 아닙니다."));
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하는 게시물이 아닙니다."));
        if(findUser.getPosts().stream().anyMatch(post -> post.equals(findPost))) {
            findPost.patch(requestDto);
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
        Post deletePost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        postRepository.delete(deletePost);
        return new PostRespDto(deletePost);
    }

    // 특정 게시물 수정
    public PostRespDto updatePost(PostReqDto dto, Long postId) {
        Post updatePost = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        updatePost.patch(dto);
        Post savedPost = postRepository.save(updatePost);
        return new PostRespDto(savedPost);
    }
}
