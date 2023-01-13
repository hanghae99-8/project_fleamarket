package hanghae.fleamarket.controller;

import hanghae.fleamarket.service.SelectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class SelectController {

    private final SelectService selectService;

    @PostMapping("/products/select/{productId}")
    public boolean selectProduct(@PathVariable Long productId, HttpServletRequest request) {
        return selectService.selectProduct(productId, request);
    }

}
