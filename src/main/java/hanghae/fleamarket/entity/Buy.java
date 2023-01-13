package hanghae.fleamarket.entity;

import javax.persistence.*;

import hanghae.fleamarket.dto.BuyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Buy extends Timestamped{
    @Id
    @Column(name = "BUY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    public Buy(BuyRequestDto requestDto, User user, Product product) {
        phone = requestDto.getPhone();
        address = requestDto.getAddress();
        this.user = user;
        this.product = product;
    }
}
