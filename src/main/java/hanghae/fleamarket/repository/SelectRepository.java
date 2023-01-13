package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Select;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectRepository extends JpaRepository<Select, Long>, CustomSelectRepository{

    Select findByUserIdAndProductId(Long userId, Long productId);
}
