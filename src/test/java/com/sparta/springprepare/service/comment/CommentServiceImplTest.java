package com.sparta.springprepare.service.comment;

import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.repository.comment.CommentRepository;
import com.sparta.springprepare.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class CommentServiceImplTest extends DummyObject {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostRepository postRepository;

    @Test
    void findCommentOfPost_test() {

        //given
        Long postId = 1L;

        // stub 1
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        Post post = newMockPost(1L, "origin contents", user);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        // stub 2
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Comment comment1 = newMockComment(1L, "original comment 1", visit, post);
        Comment comment2 = newMockComment(2L, "original comment 2", visit, post);
        Comment comment3 = newMockComment(3L, "original comment 3", visit, post);
        List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
        PageImpl<Comment> commentPage = new PageImpl<>(comments);
        when(commentRepository.findByPost(any(Post.class), any(Pageable.class))).thenReturn(commentPage);

        //when
        List<CommentRespDto> commentRespDtoList = commentService.findCommentOfPost(postId, PageRequest.of(0, 3));
        //then
        assertThat(commentRespDtoList.get(0).getContent()).isEqualTo("original comment 1");
    }

    @Test
    void createCommentOfPost_test() {

        //given
        Long postId = 1L;
        CommentReqDto commentReqDto = new CommentReqDto();
        commentReqDto.setContent("fixed contents");
        User loginUser = newMockUser(2L, "login12", "login12!!", "login12@naver.com");
        LoginUser loginUserDetails = new LoginUser(loginUser);


        // stub 1
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        Post post = newMockPost(1L, "origin contents", user);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        // stub 2
        Comment comment = newMockComment(1L, commentReqDto.getContent(), loginUserDetails.getUser(), post);
        when(commentRepository.save(any())).thenReturn(comment);

        //when
        CommentRespDto commentRespDto = commentService.createCommentOfPost(postId, commentReqDto, loginUserDetails);

        //then
        assertThat(commentRespDto.getContent()).isEqualTo("fixed contents");
        assertThat(commentRespDto.getUserId()).isEqualTo(2L);
        assertThat(commentRespDto.getPostId()).isEqualTo(1L);
    }

    @Test
    void updateCommentOfPost_test() {

        // given
        Long commentId = 1L;
        CommentReqDto commentReqDto = new CommentReqDto();
        commentReqDto.setContent("fixed contents");
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        LoginUser loginUser = new LoginUser(user);

        // stub
        Post post = newMockPost(1L, "origin contents", user);
        Comment originComment = newMockComment(1L, "original comment 1", user, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(originComment));
        Comment FixedComment = newMockComment(1L, "fixed contents", user, post);
        when(commentRepository.save(any())).thenReturn(FixedComment);

        //when
        CommentRespDto commentRespDto = commentService.updateCommentOfPost(commentId, commentReqDto, loginUser);
        //then
        assertThat(commentRespDto.getContent()).isEqualTo("fixed contents");

    }

    @Test
    void deleteCommentOfPost_test() {

        // given
        Long commentId = 1L;
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        LoginUser loginUser = new LoginUser(user);
        // stub
        Post post = newMockPost(1L, "origin contents", user);
        Comment comment = newMockComment(1L, "original comment 1", user, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        //when
        CommentRespDto commentRespDto = commentService.deleteCommentOfPost(commentId, loginUser);
        //then
        assertThat(commentRespDto.getContent()).isEqualTo("original comment 1");

    }

    @Test
    void getAllComments_test() {

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        Post post = newMockPost(1L, "origin contents", user);
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Comment comment1 = newMockComment(1L, "original comment 1", visit, post);
        Comment comment2 = newMockComment(2L, "original comment 2", visit, post);
        Comment comment3 = newMockComment(3L, "original comment 3", visit, post);
        List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
        when(commentRepository.findAll()).thenReturn(comments);

        // when
        List<CommentRespDto> allComments = commentService.getAllComments();

        // then
        assertThat(allComments.get(0).getContent()).isEqualTo("original comment 1");

    }

    @Test
    void deleteComment_test() {

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Post post = newMockPost(1L, "origin contents", user);
        Comment comment = newMockComment(1L, "original comment 1", visit, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        CommentRespDto commentRespDto = commentService.deleteComment(1L);

        assertThat(commentRespDto.getCommentId()).isEqualTo(1L);
    }

    @Test
    void updateComment_test() {

        Long commentId = 1L;
        CommentReqDto commentReqDto = new CommentReqDto();
        commentReqDto.setContent("fixed contents");

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Post post = newMockPost(1L, "origin contents", user);
        Comment comment = newMockComment(1L, "original comment 1", visit, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        //when
        CommentRespDto commentRespDto = commentService.updateComment(commentReqDto, commentId);
        //then
        assertThat(commentRespDto.getContent()).isEqualTo("fixed contents");
    }

}