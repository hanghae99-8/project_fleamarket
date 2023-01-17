package hanghae.fleamarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hanghae.fleamarket.dto.GoogleLoginDto;
import hanghae.fleamarket.dto.LoginRequestDto;
import hanghae.fleamarket.dto.SignupRequestDto;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.service.GoogleService;
import hanghae.fleamarket.service.KakaoService;
import hanghae.fleamarket.service.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final JwtUtil jwtUtil;

    //회원가입 페이지
    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/api/user/login-page";
    }

    //로그인 페이지
    @GetMapping("/login-page")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    //로그인 성공 후 홈페이지로 이동
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }

    //카카오 로그인
    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        System.out.println("======인증코드======");
        System.out.println(code);
        String createToken = kakaoService.kakaoLogin(code);

        // Cookie 생성 및 직접 브라우저에 Set, 서버에서 쿠키를 쿠키저장소에 넣어줌
        //키값                              밸류값 , substring(bearer과 공백을 삭제)
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/api/homepage";
    }

    //구글 로그인
    @GetMapping(value = "/google/login/redirect")
    public String redirectGoogleLogin(@RequestParam(value = "code") String authCode, HttpServletResponse response) {
        String jwt = googleService.redirectGoogleLogin(authCode);

        if (jwt != null){
            Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, jwt.substring(7));
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/api/homepage";
        }
        else return "forbidden";
    }

    //접근 제한
    @GetMapping("/forbidden")
    public ModelAndView getForbidden() {
        return new ModelAndView("forbidden");
    }

    //접근 제한
    @PostMapping("/forbidden")
    public ModelAndView postForbidden() {
        return new ModelAndView("forbidden");
    }

}