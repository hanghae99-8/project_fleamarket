package hanghae.fleamarket.repository;

import hanghae.fleamarket.dto.BuyResponseDto;
import hanghae.fleamarket.entity.Buy;

import java.util.List;

public interface CustomBuyRepository {

    List<Buy> findByUserId(Long userId);

}
