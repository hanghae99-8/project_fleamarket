package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.CommentRequestDto;
import hanghae.fleamarket.dto.CommentResponseDto;
import hanghae.fleamarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{productId}")
    public CommentResponseDto createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(productId, requestDto, request);
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.updateComment(commentId, requestDto, request);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        if (commentService.deleteComment(commentId, requestDto, request)) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("본인의 댓글만 삭제할 수 있습니다", HttpStatus.BAD_REQUEST);
    }


}
