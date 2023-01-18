package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
