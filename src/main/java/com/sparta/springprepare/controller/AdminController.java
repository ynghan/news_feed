package com.sparta.springprepare.controller;

import com.sparta.springprepare.domain.UserRoleEnum;
import com.sparta.springprepare.dto.AuthorityDto;
import com.sparta.springprepare.dto.commentDto.CommentRequestDto;
import com.sparta.springprepare.dto.commentDto.CommentResponseDto;
import com.sparta.springprepare.dto.postDto.PostRequestDto;
import com.sparta.springprepare.dto.postDto.PostResponseDto;
import com.sparta.springprepare.dto.userDto.UserInfoDto;
import com.sparta.springprepare.dto.userDto.UserResponseDto;
import com.sparta.springprepare.service.CommentService;
import com.sparta.springprepare.service.PostService;
import com.sparta.springprepare.service.UserService;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured(UserRoleEnum.Authority.ADMIN)
@RestController
@RequiredTypes("/api/admin")
public class AdminController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public AdminController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    // 관리자의 전체 댓글 조회하기
    @GetMapping("/comment/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> allComments = commentService.getAllComments();
        return ResponseEntity.status(HttpStatus.OK).body(allComments);
    }
    // 관리자의 특정 댓글 삭제하기
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable(name="commentId") Long commentId) {
        CommentResponseDto deleteCommentDto = commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteCommentDto);
    }
    // 관리자의 특정 댓글 수정하기
    @PutMapping("/{commentId}/update")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable(name="commentId") Long commentId) {
        CommentResponseDto updateCommentDto = commentService.updateComment(requestDto, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(updateCommentDto);
    }
    // 관리자의 전체 게시물 조회하기
    @GetMapping("/post/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> allPosts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }
    // 관리자의 특정 게시물 삭제하기
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable(name="postId") Long postId) {
        PostResponseDto deleteDto = postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
    // 관리자의 특정 게시물 수정하기
    @PutMapping("/{postId}/update")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostRequestDto requestDto, @PathVariable(name="postId") Long postId) {
        PostResponseDto postResponseDto = postService.updatePost(requestDto, postId);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    // 관리자에 의한 전체 사용자 목록 조회
    @GetMapping("/user/all")
    @ResponseBody
    public List<UserInfoDto> getAllUsers() {
        return userService.findAllUsers();
    }

    // 관리자에 의한 사용자 삭제 가능
    @DeleteMapping("/{username}/delete")
    public ResponseEntity<UserResponseDto> deleteUserByAdmin(@PathVariable(name="username") String username) {
        UserResponseDto userResponseDto = userService.deleteUserByAdmin(username);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    // 관리자에 의한 사용자 수정 기능
    @PutMapping("/{username}/update")
    public ResponseEntity<UserResponseDto> updateUserByAdmin(@PathVariable(name="username") String username, @RequestBody UserInfoDto dto) {
        UserResponseDto userResponseDto = userService.updateUserByAdmin(username, dto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    // 관리자에 의한 사용자 권한 (user -> admin)변경
    @PutMapping("/{username}/v1/authority")
    public ResponseEntity<AuthorityDto> changeUserToAdmin(@PathVariable(name="username") String username) {
        AuthorityDto authorityDto = userService.changeUserToAdmin(username);
        return ResponseEntity.status(HttpStatus.OK).body(authorityDto);
    }

    // 관리자에 의한 사용자 권한 (admin -> user)변경
    @PutMapping("/{username}/v2/authority")
    public ResponseEntity<AuthorityDto> changeAdminToUser(@PathVariable(name="username") String username) {
        AuthorityDto authorityDto = userService.changeAdminToUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(authorityDto);
    }
}
