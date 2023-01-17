package hanghae.fleamarket.service;

import hanghae.fleamarket.dto.ProductRequestDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.jwt.JwtUtil;
import hanghae.fleamarket.repository.ProductRepository;
import hanghae.fleamarket.repository.UserRepository;
import hanghae.fleamarket.s3.S3Entity;
import hanghae.fleamarket.s3.S3Uploader;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    private S3Uploader s3Uploader;

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAllOrderByCreatedAtDesc()
                .stream().map(product ->
                        ProductResponseDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .title(product.getTitle())
                                .desc(product.getDescription())
                                .img(product.getImg())
                                .price(product.getPrice())
                                .selectCount(product.getSelectCount())
                                .isSold(product.isSold())
                                .build()).collect(Collectors.toList());
    }

    //게시글 단일 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        Product product = findProduct(productId);
        return new ProductResponseDto(product);
    }

    //게시글 저장
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, MultipartFile image,  HttpServletRequest request) throws IOException {
        Claims claims = getClaims(request);
        String username = claims.getSubject();
        User user = findUser(username);

        String imgUrl = "";
        if (!image.isEmpty()) {
            imgUrl = s3Uploader.upload(image, "images");
        }

        Product product = new Product(requestDto, imgUrl, user);

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(savedProduct);
    }

    //게시글 수정
    @Transactional
    public ProductResponseDto update(Long productId, ProductRequestDto requestDto, MultipartFile image,  HttpServletRequest request) throws IOException {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        Product product = findProduct(productId);

        String imgUrl = "";
        if (!image.isEmpty()) {
            imgUrl = s3Uploader.upload(image, "images");
        }

        if (isSameUser(username, product)) {
            product.update(requestDto, imgUrl);
            return new ProductResponseDto(product);
        }
        throw new IllegalArgumentException("본인의 글만 수정 가능합니다");
    }

    @Transactional
    public void deleteProduct(Long productId, HttpServletRequest request) {
        //사용자 검증
        Claims claims = getClaims(request);
        String username = claims.getSubject();

        Product product = findProduct(productId);

        if (isSameUser(username, product)) {
            productRepository.deleteById(productId);
        }
        throw new IllegalArgumentException("본인의 글만 삭제 가능합니다");

    }

    //게시글 작성자 본인인지 확인
    private boolean isSameUser(String username, Product product) {
        return username.equals(product.getUser().getUsername());
    }

    //상품게시글 찾기
    private Product findProduct(Long productId) {
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
}

