package com.sparta.springprepare.util;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.repository.CommentRepository;
import com.sparta.springprepare.repository.PostRepository;
import com.sparta.springprepare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityCheckUtil {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    public User checkUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    public Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    public Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }
}
