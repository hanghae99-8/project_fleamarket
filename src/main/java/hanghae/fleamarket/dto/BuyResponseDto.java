package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Buy;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BuyResponseDto {

    private Long id;
    private String productName;
    private String img;
    private String sellerName;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public BuyResponseDto(Buy buy) {
        id = buy.getId();
        productName = buy.getProduct().getName();
        sellerName = buy.getUser().getUsername();
        createdAt = buy.getCreatedAt();
        phone = buy.getPhone();
        address = buy.getAddress();
        img = buy.getProduct().getImg();
    }
}
