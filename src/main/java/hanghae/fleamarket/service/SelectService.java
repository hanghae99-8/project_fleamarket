package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.Selects;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.ProductRepository;
import hanghae.fleamarket.repository.SelectRepository;
import hanghae.fleamarket.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelectService {

    private final SelectRepository selectRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    @Transactional //찜하기
    public boolean selectProduct(Long productId, HttpServletRequest request) {


        Claims claims = getClaims(request);
        String username = claims.getSubject();

        //토큰의 현재 사용자 객체 가져오기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        // id로 product 찾아오기
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 판매글입니다.")
        );

        //찜하기 이력이 있는지 찾아오기
        Selects selects = selectRepository.findByUserIdAndProductId(user.getId(), productId);


        //찜하기 이력이 없으면 새로 추가
        if (selects == null) {
            selects = new Selects(product, user);
            selectRepository.save(selects);  //찜하기 테이블에 저장
            productRepository.selectProduct(productId); //판매글  찜하기 갯수 +1
            return true;
        }

        log.info("현재 좋아요 했나 안했나? {}", selects.getStatus());
        // 이미 찜하기 한 상태
        if (selects.getStatus()) {

            selectRepository.cancelSelect(selects.getId());//찜하기 테이블 상태 false
            productRepository.cancelSelect(productId); //판매글 찜하기 갯수 -1
            return false;

        } else { //찜하기 취소된 상태
            selectRepository.selectProduct(selects.getId()); //찜하기 테이블 상태 true
            productRepository.selectProduct(productId); //판매글  찜하기 갯수 +1
            return true;
        }
    }

    @Transactional(readOnly = true) //찜하기 목록
    public List<ProductResponseDto> findAll(HttpServletRequest request) {

        Claims claims = getClaims(request);
        String username = claims.getSubject();

        //토큰의 현재 사용자 객체 가져오기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        // 현재 사용자의 찜하기 목록 다 가져오기
        return selectRepository.findAllByUserId(user.getId());
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

//        jwtUtil.validateToken(token);
        return jwtUtil.getUserInfoFromToken(token);
    }
}
