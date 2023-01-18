package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.BuyRequestDto;
import hanghae.fleamarket.dto.BuyResponseDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.repository.UserRepository;
import hanghae.fleamarket.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BuyController {

    private final BuyService buyService;
    private final UserRepository userRepository;

    // 구매하기 화면
    @GetMapping("/products/{productId}/buy")
    public ProductResponseDto getProduct(@PathVariable Long productId, HttpServletRequest request) {
        return buyService.getProduct(productId, request);

    }

    //구매하기
    @PostMapping("/products/{productId}/buy")
    public BuyResponseDto buyProduct(@PathVariable Long productId, @RequestBody BuyRequestDto requestDto, HttpServletRequest request) {
        return buyService.buyProduct(productId, requestDto, request);
    }

    //구매내역보기
    @GetMapping("/products/buy-list")
    public List<BuyResponseDto> findBuyProducts(HttpServletRequest request) {
        return buyService.findBuyProducts(request);
    }

}
