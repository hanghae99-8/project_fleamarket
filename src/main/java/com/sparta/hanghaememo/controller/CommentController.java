package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentRequest;
import com.sparta.hanghaememo.dto.CommentResponse;
import com.sparta.hanghaememo.dto.StatusResponse;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sparta/{id}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentResponse addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        return commentService.addComment(id, commentRequest, request);
    }

    @PatchMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        return commentService.updateComment(id, commentId, commentRequest, request);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<StatusResponse> deleteComment(@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request) {
        StatusResponse comment = commentService.deleteComment(id, commentId, request);
        return new ResponseEntity<>(comment, HttpStatus.valueOf(comment.getStatusCode()));
    }
}