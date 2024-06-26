package com.sparta.springprepare.service.post;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sparta.springprepare.dto.postDto.PostRespDto.*;

public interface PostService {

    // 사용자 게시물 등록하기
    PostRespDto createPost(PostReqDto requestDto, Long userId);
    // 특정 사용자 게시물 목록 불러오기
    Page<PostRespDto> getPosts(User user, Pageable pageable);
    // 특정 게시물 자세히 보기
    PostDetailRespDto getDetailPost(User user, Long postId);
    // 조회할 사용자가 로그인 사용자가 팔로우한 사용자 목록에 포함되어 있어야 사용자의 게시물을 조회할 수 있다.
    Page<PostRespDto> getUserPosts(String followUsername, User loginUser, Pageable pageable);
    // 게시물 개수 불러오기
    CountDto getPostCount(User user, Pageable pageable);
    // 특정 게시물 수정하기
    PostRespDto updatePost(Long postId, PostReqDto requestDto, User user);
    // 전체 게시물 조회
    List<PostRespDto> getAllPosts();
    // 특정 게시물 삭제
    PostRespDto deletePost(Long postId);
    // 특정 게시물 수정
    PostRespDto updateAnyPost(PostReqDto dto, Long postId);

}