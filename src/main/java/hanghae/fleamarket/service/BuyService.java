package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.BuyRequestDto;
import hanghae.fleamarket.dto.BuyResponseDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Buy;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.BuyRepository;
import hanghae.fleamarket.repository.ProductRepository;
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
public class BuyService {

    private final ProductRepository productRepository;
    private final BuyRepository buyRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //구매하기 화면
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId, HttpServletRequest request) {
        Product product = findProduct(productId);
        return new ProductResponseDto(product);
    }

    //구매하기
    @Transactional
    public BuyResponseDto buyProduct(Long productId, BuyRequestDto requestDto, HttpServletRequest request) {

        Claims claims = getClaims(request);
        String username = claims.getSubject();

        log.info("phone = {}", requestDto.getPhone());
        log.info("address = {}", requestDto.getAddress());

        //토큰의 현재 사용자 객체 가져오기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        //제품 가져와서 isSold(판매완료) 업데이트하기
        Product product = findProduct(productId);
        productRepository.soldProduct(product.getId());

        Buy buy = new Buy(requestDto, user, product);
        buyRepository.save(buy);
        return new BuyResponseDto(buy);
    }

    @Transactional(readOnly = true)
    public List<BuyResponseDto> findBuyProducts(HttpServletRequest request) {

        Claims claims = getClaims(request);
        String username = claims.getSubject();

        //토큰의 현재 사용자 객체 가져오기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        //사용자 id로 구매내역 조회하기
        return buyRepository.findByUserId(user.getId());
    }

    //제품 검색
    private Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 판매글입니다.")
        );
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

//        jwtUtil.validateToken(token);
        return jwtUtil.getUserInfoFromToken(token);
    }
}
