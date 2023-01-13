package hanghae.fleamarket.repository;

import hanghae.fleamarket.dto.ProductResponseDto;

import java.util.List;

public interface CustomSelectRepository {

    void cancelSelect(Long selectId);

    void selectProduct(Long selectId);

    List<ProductResponseDto> findAllByUserId(Long userId);
}
