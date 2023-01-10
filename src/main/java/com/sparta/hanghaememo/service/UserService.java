package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

//    @Transactional
//    public StatusResponse signup(SignupRequestDto signupRequestDto) {
//        String username = signupRequestDto.getUsername();
//        String password = signupRequestDto.getPassword();
//
//        // 회원 중복 확인
//        Optional<User> found = userRepository.findByUsername(username);
//        if (found.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
//        }
//
//        User user = new User(username, password);
//        userRepository.save(user);
//
//        return new StatusResponse("회원가입 성공", HttpStatus.OK.value());
//    }

//    @Transactional(readOnly = true)
//    public StatusResponse login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        String username = loginRequestDto.getUsername();
//        String password = loginRequestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//        // 비밀번호 확인
//        if(!user.getPassword().equals(password)){
//            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));
//
//        return new StatusResponse("로그인 성공",HttpStatus.OK.value());
//    }
}