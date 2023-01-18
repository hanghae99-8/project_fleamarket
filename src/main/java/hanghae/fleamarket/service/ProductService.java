package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.ProductRequestDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Image;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.ImageRepository;
import hanghae.fleamarket.repository.ProductRepository;
import hanghae.fleamarket.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ImageRepository imageRepository;

    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    //게시글 단일 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = checkProduct(productId);
        return new ProductResponseDto(product);
    }

    //게시글 저장
    @Transactional
    public ResponseEntity<String> createProduct(ProductRequestDto requestDto, HttpServletRequest request) throws IOException {
        Claims claims = getClaims(request);
        String username = claims.getSubject();
        User user = findUser(username);

        //이미지 id로 url 가져오기
//        Image image = imageRepository.findById(requestDto.getImgId()).orElseThrow(
//                () -> new IllegalArgumentException("이미지가 존재하지 않습니다")
//        );

//        Optional<Image> image = imageRepository.findById(requestDto.getImgId());
//        String imgUrl = "";
//        if (image.isPresent()) {
//            imgUrl = image.get().getImgUrl();
//        }

        // requestDto에 이미지 url을 포함한 정보가 있음
        Product product = requestDto.toEntity(user);
        productRepository.save(product);

        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
    }

    //게시글 수정
    @Transactional
    public ProductResponseDto update(Long productId, ProductRequestDto requestDto, HttpServletRequest request) throws IOException {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        Product product = checkProduct(productId);

        if (isSameUser(username, product)) {
            product.update(requestDto);
            return new ProductResponseDto(product);
        }
        throw new IllegalArgumentException("접근 권한이 없습니다(본인의 글만 수정 가능합니다)");
    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Long productId, HttpServletRequest request) {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        Product product = checkProduct(productId);

        if (isSameUser(username, product)) {
            productRepository.deleteById(productId);
            return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
        }
        throw new IllegalArgumentException("접근 권한이 없습니다(본인의 글만 삭제 가능합니다)");

    }

    //게시글 작성자 본인인지 확인
    private boolean isSameUser(String username, Product product) {
        return username.equals(product.getUser().getUsername());
    }

    //상품게시글 찾기
    private Product checkProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
    }

    //사용자 찾기
    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        return jwtUtil.getUserInfoFromToken(token);
    }

    public Long saveImage(String imgUrl) {
        Image image = new Image();
        image.setImgUrl(imgUrl);
        Image savedImage = imageRepository.save(image);
        return savedImage.getId();
    }
}

