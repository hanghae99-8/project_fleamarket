package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private Long id;
    private String content;

}
