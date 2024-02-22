package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.FollowDto;

import java.util.List;

public interface FollowService {

    List<FollowDto> findFollowListOfUser(String username);

    List<FollowDto> findFolloweeListOfUser(String username);

    FollowDto addFollower(String followeeUsername, User loginUser);

    FollowDto deleteFollowee(String followeeUsername, User loginUser);

}