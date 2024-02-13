package com.sparta.springprepare.service;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.postDto.PostRequestDto;
import com.sparta.springprepare.dto.postDto.PostResponseDto;
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

    // 조회할 사용자가 로그인 사용자가 팔로우한 사용자 목록에 포함되어 있어야 사용자의 게시물을 조회할 수 있다.
    @Transactional
    public List<PostResponseDto> getUserPosts(String followUsername, User loginUser) {
        Long findUserId = userRepository.findByUsername(followUsername).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")).getId();
        loginUser = userRepository.findById(loginUser.getId()).get();

        Follow findFollowEntity = loginUser.getFollowers()
                .stream().filter(follow -> follow.getFollowee().getId().equals(findUserId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 사용자는 팔로우한 사용자가 아닙니다."));

        List<Post> postList = postRepository.findAllByUser(findFollowEntity.getFollowee());
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
