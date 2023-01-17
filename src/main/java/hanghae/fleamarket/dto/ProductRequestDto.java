package hanghae.fleamarket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {


    private String name;
    private String title;

    private String description;

    private int price;
}
