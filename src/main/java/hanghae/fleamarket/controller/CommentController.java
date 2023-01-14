package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.CommentRequestDto;
import hanghae.fleamarket.dto.CommentResponseDto;
import hanghae.fleamarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(productId, requestDto, request);
    }
}
