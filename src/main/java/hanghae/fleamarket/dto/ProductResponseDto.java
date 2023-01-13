package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Product;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
public class ProductResponseDto {

    private Long id;
    private String name;
    private String title;

    private String desc;
    private String img;

    private int price;
    private int selectCount;
    private boolean isSold;

    public ProductResponseDto(Product product) {
        id = product.getId();
        name = product.getName();
        title = product.getTitle();
        desc = product.getDescription();
        img = product.getImg();
        price = product.getPrice();
        selectCount = product.getSelectCount();
        isSold = product.isSold();
    }
}
