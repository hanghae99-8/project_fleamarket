package hanghae.fleamarket.repository;

import hanghae.fleamarket.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
