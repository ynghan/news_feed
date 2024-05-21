package com.sparta.springprepare.service.follow;

import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;
import com.sparta.springprepare.repository.follow.FollowRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import com.sparta.springprepare.service.follow.Impl.FollowServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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
class FollowServiceImplTest extends DummyObject {

    @InjectMocks
    FollowServiceImpl followService;

    @Mock
    UserRepository userRepository;
    @Mock
    FollowRepository followRepository;

    @Test
    void findFollowListOfUser_test() throws Exception {

        //given
        String username = "dudgks1";


        // stub
        User user1 = newMockUser(1L, "dudgks1", "dudgks56!!", "daum14572@naver.com");
        User user2 = newMockUser(2L, "dudgks2", "dudgks56!!", "daum24572@naver.com");
        User user3 = newMockUser(3L, "dudgks3", "dudgks56!!", "daum34572@naver.com");
        User user4 = newMockUser(4L, "dudgks4", "dudgks56!!", "daum44572@naver.com");

        Follow follow1 = newMockFollow(1L, user1, user2);
        Follow follow2 = newMockFollow(2L, user1, user3);
        Follow follow3 = newMockFollow(3L, user1, user4);

        List<Follow> follows = Arrays.asList(follow1, follow2, follow3);
        PageImpl<Follow> followPage = new PageImpl<>(follows);
        when(userRepository.findWithFollowersByUsername(any(String.class), any(Pageable.class))).thenReturn(followPage);

        //when
        Page<FollowDto> dtoPage = followService.findFollowListOfUser(username, PageRequest.of(0, 3));
        //then
        assertThat(dtoPage.getContent().get(0).getUsername()).isEqualTo("dudgks2");
        assertThat(dtoPage.getContent().get(1).getUsername()).isEqualTo("dudgks3");
        assertThat(dtoPage.getContent().get(2).getUsername()).isEqualTo("dudgks4");
    }

    @Test
    void findFolloweeListOfUser_test() throws Exception {
        //given
        String username = "dudgks1";

        // stub
        User user1 = newMockUser(1L, "dudgks1", "dudgks56!!", "daum14572@naver.com");
        User user2 = newMockUser(2L, "dudgks2", "dudgks56!!", "daum24572@naver.com");
        User user3 = newMockUser(3L, "dudgks3", "dudgks56!!", "daum34572@naver.com");
        User user4 = newMockUser(4L, "dudgks4", "dudgks56!!", "daum44572@naver.com");

        Follow follow1 = newMockFollow(1L, user2, user1);
        Follow follow2 = newMockFollow(2L, user3, user1);
        Follow follow3 = newMockFollow(3L, user4, user1);

        List<Follow> follows = Arrays.asList(follow1, follow2, follow3);
        PageImpl<Follow> followPage = new PageImpl<>(follows);
        when(userRepository.findWithFolloweesByUsername(any(), any())).thenReturn(followPage);

        //when
        Page<FollowDto> dtoPage = followService.findFolloweeListOfUser(username, PageRequest.of(0, 3));
        //then
        assertThat(dtoPage.getContent().get(0).getUsername()).isEqualTo("dudgks2");
        assertThat(dtoPage.getContent().get(1).getUsername()).isEqualTo("dudgks3");
        assertThat(dtoPage.getContent().get(2).getUsername()).isEqualTo("dudgks4");

    }

    @Test
    void addFollower_test() throws Exception {
        //given
        String followeeUsername = "dudgks5";

        //stub

        User loginUser = newMockUser(1L, "dudgks1", "dudgks56!!", "daum14572@naver.com");
        User user2 = newMockUser(2L, "dudgks2", "dudgks56!!", "daum24572@naver.com");
        User user3 = newMockUser(3L, "dudgks3", "dudgks56!!", "daum34572@naver.com");
        User user4 = newMockUser(4L, "dudgks4", "dudgks56!!", "daum44572@naver.com");
        User user5 = newMockUser(5L, "dudgks5", "dudgks56!!", "daum44572@naver.com");

        Follow follow1 = newMockFollow(1L, loginUser, user2);
        Follow follow2 = newMockFollow(2L, loginUser, user3);
        Follow follow3 = newMockFollow(3L, loginUser, user4);
        List<Follow> follows = Arrays.asList(follow1, follow2, follow3);
        loginUser.setFollowers(follows);

        when(userRepository.findById(any())).thenReturn(Optional.of(loginUser));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user2));

        Follow follow = newMockFollow(4L, loginUser, user5);
        when(followRepository.save(any())).thenReturn(follow);
        //when
        FollowDto followDto = followService.addFollower(followeeUsername, loginUser);
        //then
        assertThat(followDto.getUsername()).isEqualTo("dudgks5");

    }

    @Test
    void deleteFollowee_test() throws Exception {
        //given
        String followeeUsername = "dudgks2";
        User loginUser = newMockUser(1L, "dudgks1", "dudgks56!!", "daum14572@naver.com");

        //stub
        User user2 = newMockUser(2L, "dudgks2", "dudgks56!!", "daum24572@naver.com");
        User user3 = newMockUser(3L, "dudgks3", "dudgks56!!", "daum34572@naver.com");
        User user4 = newMockUser(4L, "dudgks4", "dudgks56!!", "daum44572@naver.com");

        Follow follow1 = newMockFollow(1L, loginUser, user2);
        Follow follow2 = newMockFollow(2L, loginUser, user3);
        Follow follow3 = newMockFollow(3L, loginUser, user4);
        List<Follow> follows = Arrays.asList(follow1, follow2, follow3);
        loginUser.setFollowers(follows);

        when(userRepository.findById(any())).thenReturn(Optional.of(loginUser));

        //when
        FollowDto followDto = followService.deleteFollowee(followeeUsername, loginUser);

        //then
        assertThat(followDto.getUsername()).isEqualTo("dudgks2");
    }
}