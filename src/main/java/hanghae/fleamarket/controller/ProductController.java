package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.ProductRequestDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //게시글 전체 조회
    @GetMapping("/api/products")
    public List<ProductResponseDto> findAllProducts() {
        return productService.findAllProducts();
    }

    //게시글 단일 조회
    @GetMapping("/api/products/{productId}")
    public ProductResponseDto findProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    //게시글 등록
    @PostMapping(value = "/api/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, @RequestParam MultipartFile image, HttpServletRequest request) throws IOException {
        return productService.createProduct(requestDto, image, request);
    }

    //게시글 수정
    @PutMapping(value = "/api/products/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto requestDto, @RequestParam MultipartFile image, HttpServletRequest request) throws IOException {

        return productService.update(productId, requestDto, image, request);
    }

    //게시글 삭제
    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, HttpServletRequest request) {
        productService.deleteProduct(productId, request);
        return new ResponseEntity<>("삭제 성공", HttpStatus.CREATED);
    }
}
