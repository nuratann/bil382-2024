package kg.buyers.elasticservice.repositories;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;

import java.io.IOException;
import java.util.List;

public interface ProductRepository{
    void save(Product product) throws IOException;
    void bulk(List<Product> products) throws IOException;
    Product findById(String id) throws IOException;
}
