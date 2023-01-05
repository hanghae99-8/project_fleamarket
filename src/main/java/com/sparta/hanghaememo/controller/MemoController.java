package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.StatusResponse;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }


    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request) {
        return memoService.createMemo(requestDto,request);
    }

    @GetMapping("/api/memos")
    public List<Memo> getMemo() {
        return memoService.getMemo();
    }

    @GetMapping("/api/memos/{id}")
    public MemoResponseDto getDetail(@PathVariable Long id){
        return memoService.getDetail(id);
    }

    @PatchMapping("/api/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.update(id, requestDto);
    }

    @DeleteMapping("/api/memos/{id}")
    public ResponseEntity<StatusResponse> deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        StatusResponse memo=memoService.deleteMemo(id, request);
        return new ResponseEntity<>(memo, HttpStatus.valueOf(memo.getStatusCode()));
    }


}
