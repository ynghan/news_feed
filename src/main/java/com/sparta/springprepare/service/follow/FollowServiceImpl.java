package com.sparta.springprepare.service.follow;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;
import com.sparta.springprepare.handler.ex.CustomApiException;
import com.sparta.springprepare.handler.ex.ErrorCode;
import com.sparta.springprepare.repository.FollowRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    // 특정 사용자가 팔로우한 리스트 조회하기
    public Page<FollowDto> findFollowListOfUser(String username, Pageable pageable) {

        Page<Follow> findFollowerEntities = userRepository.findWithFollowersByUsername(username, pageable);

        return findFollowerEntities.map(f -> (new FollowDto(f.getFollowee())));
    }

    // 특정 사용자를 팔로우한 리스트 조회하기
    public Page<FollowDto> findFolloweeListOfUser(String username, Pageable pageable) {
        Page<Follow> findFolloweeEntities = userRepository.findWithFolloweesByUsername(username, pageable);


        return findFolloweeEntities.map(f -> (new FollowDto(f.getFollower())));
    }

    // 로그인 사용자가 특정 사용자를 팔로우 목록에 추가.
    @Transactional
    public FollowDto addFollower(String followeeUsername, User loginUser) {
        User followeeUser = checkUserByUsername(followeeUsername);
        loginUser = checkUserById(loginUser.getId());

        boolean alreadyFollowing = loginUser.getFollowers().stream()
                .anyMatch(f -> f.getFollowee().getUsername().equals(followeeUsername));

        if (alreadyFollowing) { // 중복 검사
            throw new CustomApiException(ErrorCode.ALREADY_FOLLOWING_USER);
        }

        Follow save = followRepository.save(new Follow(loginUser, followeeUser));
        return new FollowDto(save.getFollowee());
    }


    // 로그인 사용자가 팔로우한 사용자를 팔로우 목록에서 제거
    @Transactional
    public FollowDto deleteFollowee(String followeeUsername, User loginUser) {
        loginUser = checkUserById(loginUser.getId());
        Follow follow = loginUser.getFollowers().stream() // 포함 유무 검사
                .filter(f -> f.getFollowee().getUsername().equals(followeeUsername))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ErrorCode.NOT_FOLLOWING_USER));

        followRepository.delete(follow);

        return new FollowDto(follow.getFollowee());
    }


    private User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }

}
