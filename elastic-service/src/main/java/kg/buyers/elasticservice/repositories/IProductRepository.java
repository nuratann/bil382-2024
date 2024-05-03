package kg.buyers.elasticservice.repositories;

import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.Query;
import kg.buyers.elasticservice.entities.Suggestion;

import java.io.IOException;
import java.util.List;

public interface IProductRepository{
    void save(Product product) throws IOException;
    void bulk(List<Product> products) throws IOException;
    void suggestionBulk(List<Suggestion> suggestions) throws IOException;
    Product findById(String id) throws IOException;
    List<Product> findAll() throws IOException;
    List<Product> searchByString(String query) throws IOException;
    List<Suggestion> suggest(String prefix) throws IOException;
}
