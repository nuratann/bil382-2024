package kg.buyers.productservice.repositories;

import kg.buyers.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, String> {
}
