package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.dto.StatusResponse;
import com.sparta.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StatusResponse> signup(@RequestBody SignupRequestDto signupRequestDto) {
        StatusResponse user=userService.signup(signupRequestDto);
        return new ResponseEntity<>(user, HttpStatus.valueOf(user.getStatusCode()));
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<StatusResponse> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        StatusResponse user = userService.login(loginRequestDto, response);
        return new ResponseEntity<>(user,HttpStatus.valueOf(user.getStatusCode()));
    }

}