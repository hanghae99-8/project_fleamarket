package hanghae.fleamarket.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "selects")
@NoArgsConstructor
public class Select {
    @Id
    @Column(name = "SELECTS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Select(Product product, User user) {
        this.product = product;
        this.user = user;
        status = true;
    }

}

