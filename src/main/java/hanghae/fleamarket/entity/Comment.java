package hanghae.fleamarket.entity;

import hanghae.fleamarket.dto.CommentRequestDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentDto) {
        content = commentDto.getContent();
    }

    public Comment createComment(Product product, Comment comment, User user) {
        this.product = product;
        this.user = user;
        List<Comment> comments = product.getComments();
        comments.add(comment);
        product.setComments(comments);
        return comment;
    }

    public void update(CommentRequestDto requestDto) {
        content = requestDto.getContent();
    }
}
