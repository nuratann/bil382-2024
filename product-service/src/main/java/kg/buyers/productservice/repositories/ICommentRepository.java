package kg.buyers.productservice.repositories;

import kg.buyers.productservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, String> {
}
