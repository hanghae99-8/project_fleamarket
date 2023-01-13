package hanghae.fleamarket.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Select {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "PRDUCT_ID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "USR_ID", nullable = false)
    private User user;

}

