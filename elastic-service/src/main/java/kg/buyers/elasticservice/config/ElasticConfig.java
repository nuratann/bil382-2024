package kg.buyers.elasticservice.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.net.ssl.SSLContext;
import java.io.*;

@Configuration
public class ElasticConfig {

    @Value("${elastic.apiKey}")
    private String apiKey;

    @Value("${elastic.username}")
    private String username;

    @Value("${elastic.password}")
    private String password;
    @Value("${elastic.host}")
    private String host;
    @Value("${elastic.port}")
    private String port;

    private final ResourceLoader resourceLoader;

    public ElasticConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Bean
    public ElasticsearchClient elasticsearchClient() throws IOException {
        String projectDirectory = System.getProperty("user.dir");
        File certFile = new File(projectDirectory+"/elastic-service/es01.crt");

        SSLContext sslContext = TransportUtils
                .sslContextFromHttpCaCrt(certFile);

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(username, password)
        );

        RestClient restClient = RestClient
                .builder(new HttpHost(host, Integer.parseInt(port), "https"))
                .setHttpClientConfigCallback(hc -> hc
                        .setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credentialsProvider)
                )
                .build();


        // Create the transport and the API client
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient esClient =  new ElasticsearchClient(transport);

        //create products index in es
        InputStream productsIndexCreateReqJson = resourceLoader.getResource("classpath:productsIndexCreate.json").getInputStream();

        CreateIndexRequest productsCreateReq = CreateIndexRequest.of(b -> b
                .index("products")
                .withJson(productsIndexCreateReqJson)
        );
        boolean products = esClient.indices().create(productsCreateReq).acknowledged();
        if(!products) throw new RuntimeException("error when create products index ^_^");

        //create suggestions index in es
        InputStream suggestionsIndexCreateReqJson = resourceLoader.getResource("classpath:suggestionsIndexCreate.json").getInputStream();

        CreateIndexRequest suggestionsCreateReq = CreateIndexRequest.of(b -> b
                .index("suggestions")
                .withJson(suggestionsIndexCreateReqJson)
        );
        boolean suggestions = esClient.indices().create(suggestionsCreateReq).acknowledged();
        if(!suggestions) throw new RuntimeException("error when create suggestions index ^_^");

        //create sellers index in es
        InputStream sellersIndexCreateReqJson = resourceLoader.getResource("classpath:sellersIndexCreate.json").getInputStream();

        CreateIndexRequest sellersCreateReq = CreateIndexRequest.of(b -> b
                .index("sellers")
                .withJson(sellersIndexCreateReqJson)
        );
        boolean sellers = esClient.indices().create(sellersCreateReq).acknowledged();
        if(!sellers) throw new RuntimeException("error when create sellers index ^_^");

        return esClient;
    }

}