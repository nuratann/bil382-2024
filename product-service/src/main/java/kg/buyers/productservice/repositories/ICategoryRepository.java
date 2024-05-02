package kg.buyers.productservice.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import kg.buyers.productservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
