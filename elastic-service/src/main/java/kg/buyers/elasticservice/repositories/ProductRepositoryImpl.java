package kg.buyers.elasticservice.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.json.JsonData;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.Suggestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ProductRepositoryImpl implements IProductRepository{
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

        esClient.bulk(br.build());
    }

    @Override
    public void suggestionBulk(List<Suggestion> suggestions) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Suggestion suggestion : suggestions) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("suggestions")
                            .document(suggestion)
                    )
            );
        }

        esClient.bulk(br.build());
    }

    @Override
    public Product findById(String id) throws IOException {
        GetResponse<Product> response = esClient.get(g -> g
                        .index("products")
                        .id(id),
                Product.class
        );

        if (response.found()) {
            assert response.source() != null;
            return response.source();
        } else {
            return null;
        }
    }

    @Override
    public List<Product> findAll() throws IOException {
        SearchResponse<Product> response = esClient.search(s -> s
                        .index("products")
                        .size(100)
                        .query(q -> q
                                .matchAll(t -> t)
                        ),
                Product.class
        );
        return getProductsFromHits(response.hits().hits());
    }

    private List<Product> getProductsFromHits(List<Hit<Product>> hits) {
        List<Product> products = new ArrayList<>();
        for (Hit<Product> hit: hits) {
            assert hit.source() != null;
            products.add(hit.source());
        }
        return products;
    }

    @Override
    public List<Product> searchByString(String query) throws IOException {
        SearchTemplateResponse<Product> response = esClient.searchTemplate(r -> r
                        .index("products")
                        .id("wildcard_search")
                        .params("value", JsonData.of(query)),
                Product.class
        );

        return getProductsFromHits(response.hits().hits());
    }


    @Override
    public List<Suggestion> suggest(String prefix) throws IOException {
        SearchTemplateResponse<Suggestion> response = esClient.searchTemplate(r -> r
                        .index("queries")
                        .id("suggestQuery")
                        .params("prefix", JsonData.of(prefix)),
                Suggestion.class
        );

        List<CompletionSuggestOption<Suggestion>> options = response.suggest().get("my-suggest").get(0).completion().options();
        List<Suggestion> queries = new ArrayList<>();
        for (CompletionSuggestOption<Suggestion> option : options) {
            queries.add(option.source());
        }

        return queries;
    }
}