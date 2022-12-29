package com.sparta.hanghaememo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String users;
    private String titles;
    private String contents;

    public MemoResponseDto(LocalDateTime createdAt,LocalDateTime modifiedAt,String users,String titles,String contents){
        this.createdAt=createdAt;
        this.modifiedAt=modifiedAt;
        this.users=users;
        this.titles=titles;
        this.contents=contents;
    }
}
