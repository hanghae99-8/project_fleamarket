package com.sparta.miniproject.dto;

import com.sparta.miniproject.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String users;
    private String titles;
    private String contents;

    public MemoResponseDto(Memo memo){
        this.createdAt=memo.getCreatedAt();
        this.modifiedAt=memo.getModifiedAt();
        this.users=memo.getUsers();
        this.titles=memo.getTitles();
        this.contents=memo.getContents();
    }
}
