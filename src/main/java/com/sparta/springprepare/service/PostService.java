package com.sparta.springprepare.service;


import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.dto.PostRequestDto;
import com.sparta.springprepare.dto.PostResponseDto;
import com.sparta.springprepare.repository.PostRepository;
import com.sparta.springprepare.repository.UserRepository;
import org.springframework.stereotype.Service;

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
    public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = new Post();
        post.setContent(requestDto.getContent());
        post.setUser(findUser);
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
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

    public List<PostResponseDto> getUserPosts(Long userId) {
        User findUser = userRepository.findById(userId).get();
        List<Post> postList = postRepository.findAllByUser(findUser);
        ArrayList<PostResponseDto> responseDtoList = new ArrayList<>();
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
