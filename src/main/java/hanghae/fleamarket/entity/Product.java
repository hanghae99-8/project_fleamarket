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
    private String name;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setSelectCount(int count) {
        selectCount += count;
    }

    public Product(ProductRequestDto requestDto, String imgUrl, User user) {
        name = requestDto.getName();
        title = requestDto.getTitle();
        description = requestDto.getDesc();
        price = requestDto.getPrice();
        img = imgUrl;
        selectCount = 0;
        isSold = false;
    }

    public void update(ProductRequestDto requestDto, String imgUrl) {
        name = requestDto.getName();
        title = requestDto.getTitle();
        description = requestDto.getDesc();
        price = requestDto.getPrice();
        img = imgUrl;

    }
}
