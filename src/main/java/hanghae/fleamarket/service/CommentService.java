package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.CommentRequestDto;
import hanghae.fleamarket.dto.CommentResponseDto;
import hanghae.fleamarket.entity.Comment;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.CommentRepository;
import hanghae.fleamarket.repository.ProductRepository;
import hanghae.fleamarket.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long productId, CommentRequestDto requestDto, HttpServletRequest request) {

        // 토큰에서 사용자 이름 가져오기
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        User user = findUser(username);
        Product product = findProduct(productId);

        Comment comment = new Comment(requestDto);

        //comment에 product, user 정보를 넣어줌
        comment.createComment(product, comment, user);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        //DB에서 댓글 찾아오기
        Comment comment = findComment(commentId);

        //본인이 쓴 댓글인지 확인
        if (!isSameUser(username, comment)) {
            throw new IllegalArgumentException("본인의 댓글만 수정 가능합니다");
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public boolean deleteComment(Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        //DB에서 댓글 찾아오기
        Comment comment = findComment(commentId);

        //본인이 쓴 댓글인지 확인
        if (!isSameUser(username, comment)) return false;

        //댓글 삭제
        commentRepository.deleteById(commentId);
        return true;
    }

    private boolean isSameUser(String username, Comment comment) {
        return comment.getUser().getUsername().equals(username);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        return jwtUtil.getUserInfoFromToken(token);
    }
}
