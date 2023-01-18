package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;

    public UserResponseDto(User user) {

        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
