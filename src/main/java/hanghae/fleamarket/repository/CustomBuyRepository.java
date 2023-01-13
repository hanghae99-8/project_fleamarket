package hanghae.fleamarket.repository;

import hanghae.fleamarket.dto.BuyResponseDto;

import java.util.List;

public interface CustomBuyRepository {

    List<BuyResponseDto> findByUserId(Long userId);

}
