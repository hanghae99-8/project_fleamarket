package hanghae.fleamarket.entity;

import javax.persistence.*;

import hanghae.fleamarket.dto.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Product extends Timestamped {
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String img;

    @Column
    private int price;

    @Column
    private Integer selectCount;

    @Column
    private boolean isSold;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne/*(fetch = FetchType.LAZY)*/
    private User user;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setSelectCount(int count) {
        selectCount += count;
    }

    public Product(ProductRequestDto requestDto, User user) {
        title = requestDto.getTitle();
        description = requestDto.getDescription();
        price = requestDto.getPrice();
        selectCount = 0;
        isSold = false;
        this.user = user;
    }

    public void update(ProductRequestDto requestDto) {
        title = requestDto.getTitle();
        description = requestDto.getDescription();
        price = requestDto.getPrice();

    }

    public void uploadImage(String imgUrl) {
        img = imgUrl;
    }


    public void confirmWriter(User writer) {
        //writer는 변경이 불가능하므로 이렇게만 해주어도 될듯
        this.user = writer;
    }

    @Builder
    public Product(User user, String title, String contents, String name, String imgUrl, int price) {
        this.user=user;
        this.title = title;
        this.description = contents;
        img = imgUrl;
        this.price = price;
        selectCount = 0;
        isSold = false;

    }

}
