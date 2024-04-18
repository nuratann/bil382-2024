package kg.buyers.elasticservice.services;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
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

    public Product findById(String id) throws IOException {
        return productRepository.findById(id);
    }



}