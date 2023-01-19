package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Buy;
import hanghae.fleamarket.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageDto {

    private Long userId;
    private String username;
    private String email;

    private Long buyId;
    private String name;
    private String title;
    private int price;
    private String sellerName;
    private LocalDateTime createdAt;
    private String phone;
    private String address;

    public MyPageDto(User user, Buy buy) {
        userId = user.getId();
        username = user.getUsername();
        email = user.getEmail();

        buyId = buy.getId();
        name = buy.getProduct().getName();
        title = buy.getProduct().getTitle();
        price = buy.getProduct().getPrice();
        sellerName = buy.getProduct().getUser().getUsername();
        createdAt = buy.getCreatedAt(); //구매일자
        phone = buy.getPhone();
        address = buy.getAddress();
    }
}
