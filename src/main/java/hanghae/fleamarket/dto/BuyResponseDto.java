package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Buy;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BuyResponseDto {

    private Long id;
    private String title;
    private String img;
    private String sellerName;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public BuyResponseDto(Buy buy) {
        id = buy.getId();
        sellerName = buy.getUser().getUsername();
        createdAt = buy.getCreatedAt();
        phone = buy.getPhone();
        address = buy.getAddress();
        title = buy.getProduct().getTitle();
        img = buy.getProduct().getImg();
    }
}
