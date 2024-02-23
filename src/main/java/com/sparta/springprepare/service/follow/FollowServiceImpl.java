package com.sparta.springprepare.service.follow;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;
import com.sparta.springprepare.repository.FollowRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import com.sparta.springprepare.util.EntityCheckUtil;
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
    private final EntityCheckUtil entityCheckUtil;


    // 특정 사용자의 팔로우 리스트 조회하기
    public Page<FollowDto> findFollowListOfUser(String username, Pageable pageable) {

        Page<Follow> findFollowerEntities = userRepository.findWithFollowersByUsername(username, pageable);

        return findFollowerEntities.map(FollowDto::new);
    }

    // 특정 사용자의 팔로위 리스트 조회하기
    public Page<FollowDto> findFolloweeListOfUser(String username, Pageable pageable) {
        Page<Follow> findFolloweeEntities = userRepository.findWithFolloweesByUsername(username, pageable);

        return findFolloweeEntities.map(FollowDto::new);
    }

    // 로그인 사용자가 특정 사용자를 팔로우 목록에 추가.
    @Transactional
    public FollowDto addFollower(String followeeUsername, User loginUser) {
        User followeeUser = entityCheckUtil.checkUserByUsername(followeeUsername);
        loginUser = entityCheckUtil.checkUserById(loginUser.getId());

        boolean alreadyFollowing = loginUser.getFollowers().stream()
                .anyMatch(f -> f.getFollowee().getUsername().equals(followeeUsername));

        if (alreadyFollowing) { // 중복 검사
            throw new IllegalArgumentException("이미 팔로우중인 사용자입니다.");
        }

        Follow save = followRepository.save(new Follow(followeeUser, loginUser));
        return new FollowDto(save.getFollowee());
    }


    @Transactional
    public FollowDto deleteFollowee(String followeeUsername, User loginUser) {
        loginUser = entityCheckUtil.checkUserById(loginUser.getId());
        Follow follow = loginUser.getFollowers().stream() // 포함 유무 검사
                .filter(f -> f.getFollowee().getUsername().equals(followeeUsername))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팔로우중인 사용자가 아닙니다."));

        followRepository.delete(follow);

        return new FollowDto(follow.getFollowee());
    }


}
