package kg.buyers.elasticservice.services;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.Query;
import kg.buyers.elasticservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) throws IOException {
        productRepository.save(product);
    }

    public void bulk(List<Product> products) throws IOException {
        productRepository.bulk(products);
    }

    public void queryBulk(List<Query> queries) throws IOException {
        productRepository.queryBulk(queries);
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

    public List<Query> suggestQuery(String prefix) throws IOException{
        return productRepository.suggestQuery(prefix);
    }

}