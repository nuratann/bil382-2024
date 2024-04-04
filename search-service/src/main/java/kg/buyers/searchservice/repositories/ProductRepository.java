package kg.buyers.searchservice.repositories;

import kg.buyers.searchservice.documents.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
