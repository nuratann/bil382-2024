package kg.buyers.elasticservice.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.json.JsonData;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.ProductDTO;
import kg.buyers.elasticservice.entities.Query;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.ArrayList;
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
        ProductDTO productDTO = new ProductDTO(product);
        esClient.index(i -> i
                .index("products")
                .id(productDTO.getId())
                .document(productDTO)
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
                            .document(new ProductDTO(product))
                    )
            );
        }

        BulkResponse result = esClient.bulk(br.build());
    }

    @Override
    public void queryBulk(List<Query> queries) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Query query : queries) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("queries")
                            .document(query)
                    )
            );
        }

        BulkResponse result = esClient.bulk(br.build());
    }

    @Override
    public Product findById(String id) throws IOException {
        GetResponse<ProductDTO> response = esClient.get(g -> g
                        .index("products")
                        .id(id),
                ProductDTO.class
        );

        if (response.found()) {
            assert response.source() != null;
            return new Product(response.source());
        } else {
            return null;
        }
    }

    @Override
    public List<Product> findAll() throws IOException {
        SearchResponse<ProductDTO> response = esClient.search(s -> s
                        .index("products")
                        .size(100)
                        .query(q -> q
                                .matchAll(t -> t)
                        ),
                ProductDTO.class
        );

        List<Product> products = new ArrayList<>();
        List<Hit<ProductDTO>> hits = response.hits().hits();
        for (Hit<ProductDTO> hit: hits) {
            assert hit.source() != null;
            products.add(new Product(hit.source()));
        }
        return products;
    }

    @Override
    public List<Product> searchByString(String query) throws IOException {
        SearchTemplateResponse<ProductDTO> response = esClient.searchTemplate(r -> r
                        .index("products")
                        .id("wildcard_search")
                        .params("value", JsonData.of(query)),
                ProductDTO.class
        );

        List<Product> products = new ArrayList<>();
        List<Hit<ProductDTO>> hits = response.hits().hits();
        for (Hit<ProductDTO> hit: hits) {
            assert hit.source() != null;
            products.add(new Product(hit.source()));
        }
        return products;
    }


    @Override
    public List<Query> suggestQuery(String prefix) throws IOException {
        SearchTemplateResponse<Query> response = esClient.searchTemplate(r -> r
                        .index("queries")
                        .id("suggestQuery")
                        .params("prefix", JsonData.of(prefix)),
                Query.class
        );

        List<CompletionSuggestOption<Query>> options = response.suggest().get("my-suggest").get(0).completion().options();
        List<Query> queries = new ArrayList<>();
        for (CompletionSuggestOption<Query> option : options) {
            queries.add(option.source());
        }

        return queries;
    }
}