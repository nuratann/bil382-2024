package kg.buyers.elasticservice.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository{
    ElasticsearchClient esClient;

    @Autowired
    public ProductRepositoryImpl(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    @Override
    public void save(Product product) throws IOException {
        esClient.index(i -> i
                .index("products")
                .id(product.getId())
                .document(product)
        );
    }

    @Override
    public void bulk(List<Product> products) throws IOException {

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Product product : products) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("products")
                            .id(product.getId())
                            .document(product)
                    )
            );
        }

        BulkResponse result = esClient.bulk(br.build());
    }

    @Override
    public Product findById(String id) throws IOException {
        GetResponse<Product> response = esClient.get(g -> g
                        .index("products")
                        .id(id),
                Product.class
        );

        if (response.found()) {
            return response.source();
        } else {
            return null;
        }
    }
}