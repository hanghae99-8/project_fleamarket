package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentRequest;
import com.sparta.hanghaememo.dto.CommentResponse;
import com.sparta.hanghaememo.dto.StatusResponse;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwUtil;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static com.sparta.hanghaememo.entity.Role.ADMIN;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponse addComment(Long id, CommentRequest commentRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);
            }else{throw new IllegalArgumentException("Token Error");}

            Comment comment = commentRequest.toEntity(claims.getSubject());
            comment.confirmWriter(userRepository.findByUsername(claims.getSubject()).orElseThrow(()->new IllegalArgumentException("작성자가 존재하지 않습니다..")));
            comment.confirmMemo(forumRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시물이 존재하지 않습니다.")));
            commentRepository.save(comment);
            return new CommentResponse(comment);
        } else {return null;}
    }


    public CommentResponse updateComment(Long id, Long commentId, CommentRequest commentRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Comment comment = commentRepository.findCommentByForum_IdAndId(id,commentId).orElseThrow(() -> new IllegalArgumentException("등록된 댓글이 없습니다."));
        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);
            }else{throw new IllegalArgumentException("Token Error");}

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
            if (user.getRole().equals(ADMIN)) {comment.updateComment(commentRequest);
            }else{
                if (claims.getSubject().equals(comment.getUsername())) {comment.updateComment(commentRequest);} else {throw new IllegalArgumentException("접근 권한이 없습니다.");}

            }return new CommentResponse(comment);
        }else{ return null;}
    }
    public StatusResponse deleteComment(Long id, Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Comment comment = commentRepository.findCommentByForum_IdAndId(id,commentId).orElseThrow(() -> new IllegalArgumentException("등록된 댓글이 없습니다."));
        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);} else {throw new IllegalArgumentException("Token Error");}
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

            if (user.getRole().equals(ADMIN)) {commentRepository.deleteById(commentId);
            }else{if (claims.getSubject().equals(comment.getUsername())) {commentRepository.deleteById(commentId);}else {throw new IllegalArgumentException("접근 권한이 없습니다.");}
            }return new StatusResponse("댓글 삭제 완료", HttpStatus.OK.value());}
        else{return null;}
    }



    private Memo checkMemo (Long id){
        return memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
    }


}