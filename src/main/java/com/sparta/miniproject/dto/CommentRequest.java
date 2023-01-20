package com.sparta.miniproject.dto;

import com.sparta.miniproject.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    String content;

    public Comment toEntity(String username) {
        return Comment.builder().username(username).content(content).build();
    }
}