package hanghae.fleamarket.s3;

import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repository extends JpaRepository<S3Entity, Long> {
}
