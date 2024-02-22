package com.sparta.springprepare.security;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 회원 이름으로 회원 정보를 찾아서 UserDetailsImpl에 주입
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername 메서드 동작");
        User userPS = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
        log.debug("userPS 생성");
        // 시큐리티 전용 세션에 담김 -> SecurityContextHolder에 Authentication 객체에 userPS 정보를 담음
        return new UserDetailsImpl(userPS);
    }


}
