package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class CommentResponse {
    private LocalDateTime createdAt;
    private String username;
    private String content;

    public CommentResponse(Comment comment){
        this.createdAt = comment.getCreatedAt();
        this.username = comment.getUsername();
        this.content = comment.getContent();
    }


}