package com.sparta.springprepare.service.commetlike;

import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.repository.comment.CommentRepository;
import com.sparta.springprepare.repository.commentlike.CommentLikeRepository;
import com.sparta.springprepare.repository.user.UserRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceImplTest extends DummyObject {

    @InjectMocks
    CommentLikeServiceImpl commentLikeService;
    @Mock
    CommentLikeRepository commentLikeRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;

    @Test
    void pushCommentLike_test() throws Exception {
        //given
        Long commentId = 1L;
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        // stub
        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Post post = newMockPost(1L, "post contents", user);
        Comment comment = newMockComment(1L, "origin comment contents 1", visit, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        //when
        CommentLikeDto commentLikeDto = commentLikeService.pushCommentLike(commentId, user);

        //then
        assertThat(commentLikeDto.getCommentId()).isEqualTo(1L);
        assertThat(commentLikeDto.getUserId()).isEqualTo(1L);
    }

    @Test
    void deleteCommentLike_test() throws Exception {
        //given
        Long commentId = 1L;
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        // stub
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        User visit = newMockUser(2L, "visit1", "dudgks56!!", "visit@naver.com");
        Post post = newMockPost(1L, "post contents", visit);
        Comment comment = newMockComment(1L, "original comment contents", user, post);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        User other = newMockUser(3L, "other1", "dudgks56!!", "other@naver.com");
        CommentLike commentLike1 = newMockCommentLike(1L, comment, user);
        CommentLike commentLike2 = newMockCommentLike(2L, comment, visit);
        CommentLike commentLike3 = newMockCommentLike(3L, comment, other);
        List<CommentLike> commentLikes = Arrays.asList(commentLike1, commentLike2, commentLike3);
        PageImpl<CommentLike> likePage = new PageImpl<>(commentLikes);
        when(commentLikeRepository.findByComment(any(Comment.class), any(Pageable.class))).thenReturn(likePage);

        //when
        commentLikeService.deleteCommentLike(commentId, user, PageRequest.of(0,3));

        //then
        verify(commentLikeRepository).deleteById(commentLike1.getId());
        verify(commentLikeRepository, times(1)).deleteById(any());
    }
}