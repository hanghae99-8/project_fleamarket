package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.BuyRequestDto;
import hanghae.fleamarket.dto.BuyResponseDto;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.User;
import hanghae.fleamarket.repository.UserRepository;
import hanghae.fleamarket.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuyController {

    private final BuyService buyService;
    private final UserRepository userRepository;

    @GetMapping("/products/buy/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId, HttpServletRequest request) {
        return buyService.getProduct(productId, request);

    }

    @PostMapping("/products/buy/{productId}")
    public BuyResponseDto buyProduct(@PathVariable Long productId, BuyRequestDto requestDto, HttpServletRequest request) {
        return buyService.buyProduct(productId, requestDto, request);
    }

    @GetMapping("/products/buy")
    public List<BuyResponseDto> findBuyProducts(HttpServletRequest request) {
        return buyService.findBuyProducts(request);
    }

}
