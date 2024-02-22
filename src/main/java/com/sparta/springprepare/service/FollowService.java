package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowService {

    List<FollowDto> findFollowListOfUser(String username);

    List<FollowDto> findFolloweeListOfUser(String username, Pageable pageable);

    FollowDto addFollower(String followeeUsername, User loginUser);

    FollowDto deleteFollowee(String followeeUsername, User loginUser);

}