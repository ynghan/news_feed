package com.sparta.springprepare.api;

import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.AuthorityDto;
import com.sparta.springprepare.dto.userDto.UserInfoDto;
import com.sparta.springprepare.dto.userDto.UserRespDto;
import com.sparta.springprepare.service.comment.CommentService;
import com.sparta.springprepare.service.comment.CommentServiceImpl;
import com.sparta.springprepare.service.post.PostService;
import com.sparta.springprepare.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public AdminApiController(CommentServiceImpl commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    // 관리자의 전체 댓글 조회하기
    @GetMapping("/comment/all")
    public ResponseEntity<?> getAllComments() {
        List<CommentRespDto> allComments = commentService.getAllComments();
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 전체 댓글 조회", allComments), HttpStatus.OK);
    }
    // 관리자의 특정 댓글 삭제하기
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable(name="commentId") Long commentId) {
        CommentRespDto deleteCommentDto = commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 특정 댓글 삭제", deleteCommentDto), HttpStatus.OK);
    }
    // 관리자의 특정 댓글 수정하기
    @PutMapping("/{commentId}/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentReqDto requestDto, @PathVariable(name="commentId") Long commentId) {
        CommentRespDto updateCommentDto = commentService.updateComment(requestDto, commentId);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 특정 댓글 수정", updateCommentDto), HttpStatus.OK);
    }
    // 관리자의 전체 게시물 조회하기
    @GetMapping("/post/all")
    public ResponseEntity<?> getAllPosts() {
        List<PostRespDto> allPosts = postService.getAllPosts();
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 전체 게시물 조회", allPosts), HttpStatus.OK);
    }
    // 관리자의 특정 게시물 삭제하기
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<?> deletePost(@PathVariable(name="postId") Long postId) {
        PostRespDto deleteDto = postService.deletePost(postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 특정 게시물 삭제", deleteDto), HttpStatus.OK);
    }
    // 관리자의 특정 게시물 수정하기
    @PutMapping("/{postId}/update")
    public ResponseEntity<?> updatePost(@RequestBody PostReqDto requestDto, @PathVariable(name="postId") Long postId) {
        PostRespDto postRespDto = postService.updatePost(requestDto, postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 특정 게시물 수정", postRespDto), HttpStatus.OK);
    }

    // 관리자에 의한 전체 사용자 목록 조회
    @GetMapping("/user/all")
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        List<UserInfoDto> dtoList = userService.findAllUsers();
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 전체 사용자 목록 조회", dtoList), HttpStatus.OK);
    }

    // 관리자에 의한 사용자 삭제 가능
    @DeleteMapping("/{username}/delete")
    public ResponseEntity<?> deleteUserByAdmin(@PathVariable(name="username") String username) {
        UserRespDto.GeneralRespDto userRespDto = userService.deleteUserByAdmin(username);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 사용자 삭제", userRespDto), HttpStatus.OK);
    }

    // 관리자에 의한 사용자 수정 기능
    @PutMapping("/{username}/update")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable(name="username") String username, @RequestBody UserInfoDto dto) {
        UserRespDto.GeneralRespDto userRespDto = userService.updateUserByAdmin(username, dto);
        return new ResponseEntity<>(new ResponseDto<>(1, "관리자의 사용자 수정", userRespDto), HttpStatus.OK);
    }

    // 관리자에 의한 사용자 권한 (user -> admin)변경
    @PutMapping("/{username}/v1/authority")
    public ResponseEntity<?> changeUserToAdmin(@PathVariable(name="username") String username) {
        AuthorityDto authorityDto = userService.changeUserToAdmin(username);
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 권한 변경(user -> admin)", authorityDto), HttpStatus.OK);
    }

    // 관리자에 의한 사용자 권한 (admin -> user)변경
    @PutMapping("/{username}/v2/authority")
    public ResponseEntity<?> changeAdminToUser(@PathVariable(name="username") String username) {
        AuthorityDto authorityDto = userService.changeAdminToUser(username);
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 권한 변경(admin -> user)", authorityDto), HttpStatus.OK);
    }
}
