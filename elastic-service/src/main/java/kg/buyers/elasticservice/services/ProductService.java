package kg.buyers.elasticservice.services;

import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.Suggestion;
import kg.buyers.elasticservice.repositories.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepositoryImpl productRepository;

    @Autowired
    public ProductService(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) throws IOException {
        productRepository.save(product);
    }

    public void bulk(List<Product> products) throws IOException {
        productRepository.bulk(products);
    }

    public void queryBulk(List<Suggestion> queries) throws IOException {
        productRepository.suggestionBulk(queries);
    }

    public Product findById(String id) throws IOException {
        return productRepository.findById(id);
    }

    public List<Product> findAll() throws IOException {
        return productRepository.findAll();
    }

    public List<Product> searchByString(String query) throws IOException{
        return productRepository.searchByString(query);
    }

    public List<Suggestion> suggestQuery(String prefix) throws IOException{
        return productRepository.suggest(prefix);
    }

}