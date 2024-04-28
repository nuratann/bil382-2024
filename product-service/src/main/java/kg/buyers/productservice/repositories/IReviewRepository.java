package kg.buyers.productservice.repositories;

import kg.buyers.productservice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, String> {
}
