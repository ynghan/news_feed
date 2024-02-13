package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.PasswordHistory;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import com.sparta.springprepare.dto.PasswordDto;
import com.sparta.springprepare.dto.PhotoDto;
import com.sparta.springprepare.dto.userDto.*;
import com.sparta.springprepare.repository.PasswordHistoryRepository;
import com.sparta.springprepare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
// 회원에 대한 비지니스 로직을 처리하는 클래스
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    public ProfileDto postProfile(MultipartFile file, User user) {
        // 파일을 저장할 디렉토리 경로
        String directory = "/Users/jeong-yeonghan/work/news_feed/spring-prepare/src/main/resources/static/image/";

        // 파일의 원래 이름
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // 디렉토리가 없으면 생성
            if (!Files.exists(Paths.get(directory))) {
                Files.createDirectories(Paths.get(directory));
            }

            // 파일 데이터를 디렉토리에 저장
            Path destination = Paths.get(directory + filename);
            Files.write(destination, file.getBytes());

            // 파일의 URL 또는 경로를 사용자의 프로필에 설정
            user.changeProfile(destination.toString());

            userRepository.save(user);

            return new ProfileDto(destination.toString());

        } catch (IOException e) {
            throw new RuntimeException("File saving failed!", e);
        }
    }

    @Transactional
    public CountDto getFolloweeCount(User user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        List<Follow> followees = findUser.get().getFollowees();
        int count = followees.size();
        return new CountDto(count);
    }

    @Transactional
    public CountDto getFollowerCount(User user) {
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        List<Follow> followers = findUser.get().getFollowers();
        int count = followers.size();
        return new CountDto(count);
    }

    public ProfileEncodingDto getProfile(User user) throws IOException {
        String profile = user.getProfile();
        Path path = Paths.get(profile);

        byte[] imageBytes = Files.readAllBytes(path);
        String imageBase64 = Base64.encodeBase64String(imageBytes);

        return new ProfileEncodingDto(imageBase64);
    }

    public UserInfoDto getUserInfo(User user) {
        User findUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(findUser != null) {
            return new UserInfoDto(findUser.getUsername(), findUser.getRole(), findUser.getIntroduce(), findUser.getNickname());
        }
        return null;
    }

    public UserInfoDto postUserInfo(UserInfoDto dto, User user) {
        User findUser = userRepository.findByUsername(user.getUsername()).get();
        findUser.patch(dto);
        userRepository.save(findUser);
        return new UserInfoDto(findUser);
    }

    public UserIntroduceDto getUserIntroduce(User user) {
        return new UserIntroduceDto(user.getIntroduce());
    }

    public UserInfoDto postUserIntroduce(UserInfoDto dto, User user) {
        User findUser = userRepository.findById(user.getId()).get();
        findUser.patch(dto);
        return new UserInfoDto(findUser);
    }

    public ProfileDto getPhotoUrl(String username) {
        User findUser = userRepository.findByUsername(username).get();
        String photoImage = findUser.getPhotoImage();
        return new ProfileDto(photoImage);
    }

    public PhotoDto postPhoto(String photoUrl, User user) {
        User findUser = userRepository.findByUsername(user.getUsername()).get();
        findUser.setPhotoImage(photoUrl);
        User savedUser = userRepository.save(findUser);
        return new PhotoDto(savedUser);
    }

    public List<UserInfoDto> findAllUsers() {
        List<User> findUsers = userRepository.findAll();
        ArrayList<UserInfoDto> dtoList = new ArrayList<>();
        for (User findUser : findUsers) {
            dtoList.add(new UserInfoDto(findUser));
        }
        return dtoList;
    }

    /**
     * 1. 비밀번호 중복 확인
     * 2. 확인 후, PasswordHistory 엔티티로 생성
     * 비밀번호 변경 시,
        * 3. 기존의 비밀번호를 새로 생성한 엔티티의 password 필드에 저장하고
        * 4. 새로운 비밀번호를 사용자 엔티티 password 필드에 업데이트 시켜준다.
     * 5. 그리고 PasswordHistory 엔티티 개수가 3이되면 가장 먼저 생성된 엔티티를 제거한다. -> 시간 필드 생성(Timestamped.class)
     */
    @Transactional
    public UserResponseDto changePassword(PasswordDto passwordDto, User loginUser) {

        // 비밀번호 중복 확인
        String newPassword = passwordDto.getNewPassword();
        String confirmNewPassword = passwordDto.getConfirmNewPassword();
        String currentPassword = loginUser.getPassword();

        // 2차 입력 비밀번호와 중복 확인
        if(!newPassword.equals(confirmNewPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }

        // 기존 비밀번호와 중복 확인
        if(passwordEncoder.matches(newPassword, currentPassword)) {
            throw new IllegalArgumentException("기존의 비밀번호와 동일합니다.");
        }

        // 기존 비밀번호를 새로 생성한 엔티티의 password 필드에 저장
        PasswordHistory oldPasswordHistory = new PasswordHistory(currentPassword, loginUser);
        passwordHistoryRepository.save(oldPasswordHistory);

        // loginUser와 연관된 모든 PasswordHistory 엔티티의 password 필드와 중복 확인
        boolean match = loginUser.getPassHis().stream().anyMatch(passwordHistory -> passwordEncoder.matches(newPassword, passwordHistory.getPassword()));
        if(match) {
            throw new IllegalArgumentException("최근 3번안에 사용한 비밀번호 입니다.");
        }

        // 새로운 비밀번호를 사용자 엔티티 password 필드에 업데이트
        loginUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(loginUser);

        // 그리고 PasswordHistory 엔티티 개수가 3이 되면 가장 먼저 생성된 엔티티를 제거한다.
        loginUser = userRepository.findById(loginUser.getId()).get();

        if(loginUser.getPassHis().size() == 3) {
            PasswordHistory firstPassHis = loginUser.getPassHis().stream()
                    .min(Comparator.comparing(PasswordHistory::getCreatedAt))
                    .orElseThrow(() -> new NoSuchElementException("비밀번호 변경 이력이 존재하지 않습니다."));

            passwordHistoryRepository.delete(firstPassHis);
        }
        return new UserResponseDto(loginUser);
    }
}