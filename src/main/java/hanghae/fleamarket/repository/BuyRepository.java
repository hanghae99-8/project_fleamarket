package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Long>, CustomBuyRepository {
}
