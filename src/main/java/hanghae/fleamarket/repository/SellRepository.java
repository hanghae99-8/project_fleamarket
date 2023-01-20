package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Long> {
    List<Sell> findByUserId(Long id);
}
