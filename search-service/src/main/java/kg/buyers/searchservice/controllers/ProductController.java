package kg.buyers.searchservice.controllers;

import kg.buyers.searchservice.documents.Product;
import kg.buyers.searchservice.dto.ProductDTO;
import kg.buyers.searchservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO product){
        var saved = productService.save(product);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
}
