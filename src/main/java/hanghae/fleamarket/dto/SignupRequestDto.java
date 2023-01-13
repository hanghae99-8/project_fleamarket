package hanghae.fleamarket.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    @NotNull
    private String username;

    @Pattern(regexp = "^[0-9a-zA-Z]*$",message = "알파벳 대소문자와 숫자로 입력하세요")
    @Size(min=8,max=15)
    @NotNull
    private String password;

    private boolean admin = false;
    private String adminToken = "";
    private String email = "";
}