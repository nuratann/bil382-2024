package kg.buyers.elasticservice.controllers;

import co.elastic.clients.elasticsearch.core.GetResponse;
import kg.buyers.elasticservice.entities.Product;
import kg.buyers.elasticservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/greet")
    public String greet() {
        return "Hello";
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws IOException {
        productService.save(product);
        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }
    @PostMapping("/bulk")
    public ResponseEntity<Product> bulkProducts(@RequestBody List<Product> products) throws IOException {
        productService.bulk(products);
        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }
}