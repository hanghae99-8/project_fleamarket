package hanghae.fleamarket.repository;

import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Product;

import java.util.List;

public interface CustomProductRepository {

    void selectProduct(Long productId);

    void cancelSelect(Long productId);

    void soldProduct(Long productId);
}
