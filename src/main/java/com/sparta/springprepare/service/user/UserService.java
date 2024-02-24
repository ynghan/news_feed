package com.sparta.springprepare.service.user;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    // 회원가입
    User signup(UserReqDto.JoinReqDto requestDto);

    // 멀티 데이터(S3) 등록
    ProfileDto postProfile(MultipartFile file, User user);

    // 멀티 데이터 조회
    ProfileEncodingDto getProfile(User user) throws IOException;

    // 팔로위 수 조회
    CountDto getFolloweeCount(User user);

    // 팔로워 수 조회
    CountDto getFollowerCount(User user);

    // 사용자 정보(username, role, introduce, nickname) 조회
    UserInfoDto getUserInfo(User user);

    // 사용자 정보(username, role, introduce, nickname) 등록
    UserInfoDto postUserInfo(UserInfoDto dto, User user);

    // 사용자 정보(introduce) 조회
    UserIntroduceDto getUserIntroduce(User user);

    // 사용자 정보(introduce) 등록
    void postUserIntroduce(String introduce, User user);

    // 사용자 이름으로 사진 경로 찾기 <-
    ProfileDto getPhotoUrl(String username);

    PhotoDto postPhoto(String photoUrl, User user);

    // 모든 사용자 정보 조회
    List<UserInfoDto> findAllUsers();

    // 사용자 비밀번호 변경
    UserRespDto.GeneralRespDto changePassword(UserReqDto.PasswordReqDto passwordDto, User loginUser);

    // 사용자 삭제
    UserRespDto.GeneralRespDto deleteUserByAdmin(String username);

    // 사용자 정보 갱신
    UserRespDto.GeneralRespDto updateUserByAdmin(String username, UserInfoDto dto);

    // 사용자 권한 변경(User -> Admin)
    AuthorityDto changeUserToAdmin(String username);

    // 사용자 권한 변경(Admin -> User)
    AuthorityDto changeAdminToUser(String username);

}