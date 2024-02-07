package com.sparta.springprepare.service;


import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.dto.PostRequestDto;
import com.sparta.springprepare.dto.PostResponseDto;
import com.sparta.springprepare.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 특정 사용자 게시물 등록하기
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(new Post(requestDto, user));
        return new PostResponseDto(post);
    }

    // 특정 사용자 게시물 불러오기
    public List<PostResponseDto> getPosts(User user) {
        List<Post> postList = postRepository.findAllByUser(user);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    // 게시물 개수 불러오기
    public CountDto getPostCount(User user) {
        List<Post> postList = postRepository.findAllByUser(user);

        long count = postList.size();

        return new CountDto(count);
    }

}
