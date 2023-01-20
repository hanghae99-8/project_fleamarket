package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    private Long id;

    private String title;

    private String description;
    private String imgUrl;

    private int price;

    public Product toEntity(User user) {
        return Product.builder()
                .title(title)
                .contents(description)
                .price(price)
                .imgUrl(imgUrl)
                .user(user).build();
    }
}
