package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequest;
import com.sparta.miniproject.dto.CommentResponse;
import com.sparta.miniproject.dto.StatusResponse;
import com.sparta.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memos/{id}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentResponse addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        return commentService.addComment(id, commentRequest, request);
    }

    @PatchMapping("/{commentId}")
    public CommentResponse updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest, HttpServletRequest request){
        return commentService.updateComment(id,commentId,commentRequest,request);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<StatusResponse> deleteComment(@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request){
        StatusResponse comment = commentService.deleteComment(id,commentId,request);
        return new ResponseEntity<>(comment, HttpStatus.valueOf(comment.getStatusCode()));
    }
//    @DeleteMapping("/{commentId}")
//    public StatusResponse deleteComment(@PathVariable Long id, @PathVariable Long commentId, HttpServletRequest request){
//        return commentService.deleteComment(id,commentId,request);
//    }
}