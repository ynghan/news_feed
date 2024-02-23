package com.sparta.springprepare.service.follow;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowService {

    Page<FollowDto> findFollowListOfUser(String username, Pageable pageable);

    Page<FollowDto> findFolloweeListOfUser(String username, Pageable pageable);

    FollowDto addFollower(String followeeUsername, User loginUser);

    FollowDto deleteFollowee(String followeeUsername, User loginUser);

}