package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import com.sparta.springprepare.dto.CountDto;
import com.sparta.springprepare.dto.ProfileDto;
import com.sparta.springprepare.dto.ProfileEncodingDto;
import com.sparta.springprepare.dto.SignupRequestDto;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// 회원에 대한 비지니스 로직을 처리하는 클래스
public class UserService {

    private final UserRepository userRepository;
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
        String directory = "/Users/jeong-yeonghan/work/news_feed/spring-prepare/src/main/resources/static/image";

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

}