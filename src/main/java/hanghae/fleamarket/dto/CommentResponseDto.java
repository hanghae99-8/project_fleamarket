package hanghae.fleamarket.dto;

import hanghae.fleamarket.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String commentUser;

    public CommentResponseDto(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        commentUser = comment.getUser().getUsername();
    }

}
