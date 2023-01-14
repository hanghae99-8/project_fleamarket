package hanghae.fleamarket.controller;

import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.service.SelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SelectController {

    private final SelectService selectService;

    @PostMapping("/products/select/{productId}")
    public boolean selectProduct(@PathVariable Long productId, HttpServletRequest request) {
        return selectService.selectProduct(productId, request);
    }

    @GetMapping("products/select-list")
    public List<ProductResponseDto> findAll(HttpServletRequest request) {
        return selectService.findAll(request);
    }
}
