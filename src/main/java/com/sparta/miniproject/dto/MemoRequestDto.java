package com.sparta.miniproject.dto;

import com.sparta.miniproject.entity.Memo;
import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String titles;
    private String contents;

    public Memo toEntity(String users) {
        return Memo.builder().titles(titles).contents(contents).users(users).build();
    }

}
