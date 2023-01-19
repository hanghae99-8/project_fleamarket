package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.LoginRequestDto;
import hanghae.fleamarket.dto.MyPageDto;
import hanghae.fleamarket.dto.SignupRequestDto;
import hanghae.fleamarket.dto.UserResponseDto;
import hanghae.fleamarket.entity.Buy;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.entity.UserRoleEnum;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.BuyRepository;
import hanghae.fleamarket.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final BuyRepository buyRepository;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); //패스워드를 암호화 시킴
        String email = signupRequestDto.getEmail();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            System.out.println("found: " + found.isPresent());
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER; //기본값 user
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role); //회원가입 정보를 넣은 유저객체를 생성
        userRepository.save(user); //유저 테이블 저장
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //헤더에 권한과 토큰을 넣어줌
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    @Transactional(readOnly = true)
    public List<MyPageDto> getMyPage(HttpServletRequest request) {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );

        List<Buy> buyList = buyRepository.findByUserId(user.getId());
        List<MyPageDto> response = new ArrayList<>();
        for (Buy buy : buyList) {
            response.add(new MyPageDto(buy));
        }

        return response;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(HttpServletRequest request) {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다")
        );
        return new UserResponseDto(user);
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        return jwtUtil.getUserInfoFromToken(token);
    }
}