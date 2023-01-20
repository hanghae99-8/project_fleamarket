package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Buy;
import hanghae.fleamarket.entity.Sell;
import hanghae.fleamarket.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageDto {

    private Long userId;
    private String username;
    private String email;

    private Long buyId;

    private String title;
    private int price;
    private String sellerName;
    private LocalDateTime createdAt;
    private String phone;
    private String address;

    private String buyer;
    private String sellTitle;
    private int sellPrice;
    private LocalDateTime sellCreatedAt;

    public MyPageDto(Buy buy) {
        buyId = buy.getId();
        title = buy.getProduct().getTitle();
        price = buy.getProduct().getPrice();
        sellerName = buy.getProduct().getUser().getUsername();
        createdAt = buy.getCreatedAt(); //구매일자
        phone = buy.getPhone();
        address = buy.getAddress();
    }

    public MyPageDto(Sell sell) {
        buyer = sell.getBuyer().getUsername();
        sellTitle = sell.getProduct().getTitle();
        sellPrice = sell.getProduct().getPrice();
        sellCreatedAt = sell.getCreatedAt();
    }

    public MyPageDto(User user) {

        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();

    }
}
