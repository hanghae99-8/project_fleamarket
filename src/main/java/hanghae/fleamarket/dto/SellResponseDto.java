package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Sell;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SellResponseDto {

    private Long id;
    private String title;
    private String img;
    private String buyerName;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public SellResponseDto(Sell sell) {
        id = sell.getId();
        buyerName = sell.getUser().getUsername();
        createdAt = sell.getCreatedAt();
        img = sell.getProduct().getImg();
        title = sell.getProduct().getTitle();
    }
}
