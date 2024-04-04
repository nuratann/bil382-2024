package kg.buyers.recommendationservice.repositories;

import kg.buyers.recommendationservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
