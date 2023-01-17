package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Selects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectRepository extends JpaRepository<Selects, Long>, CustomSelectRepository{

    Selects findByUserIdAndProductId(Long userId, Long productId);
}
