package kg.buyers.elasticservice.repositories;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.ProductDTO;
import kg.buyers.elasticservice.entities.Query;

import java.io.IOException;
import java.util.List;

public interface ProductRepository{
    void save(Product product) throws IOException;
    void bulk(List<Product> products) throws IOException;
    void queryBulk(List<Query> products) throws IOException;
    Product findById(String id) throws IOException;
    List<Product> findAll() throws IOException;
    List<Product> searchByString(String query) throws IOException;
    List<Query> suggestQuery(String prefix) throws IOException;
}
