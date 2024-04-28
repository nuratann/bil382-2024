package kg.buyers.elasticservice.controllers;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.entities.Query;
import kg.buyers.elasticservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/greet")
    public String greet() {
        return "Hello";
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/products/")
    public List<Product> getProducts() throws IOException {
        return productService.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/products/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws IOException {
        productService.save(product);
        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }
    @PostMapping("/products/bulk")
    public ResponseEntity<Product> bulkProducts(@RequestBody List<Product> products) throws IOException {
        productService.bulk(products);
        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }

    @PostMapping("/query/bulk")
    public ResponseEntity<Query> bulkQueries(@RequestBody List<Query> queries) throws IOException {
        productService.queryBulk(queries);
        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public List<Product> searchByString(@RequestParam String query) throws IOException {
        return productService.searchByString(query);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/suggest")
    public List<Query> suggestQuery(@RequestParam String query) throws IOException {
        return productService.suggestQuery(query);
    }
}