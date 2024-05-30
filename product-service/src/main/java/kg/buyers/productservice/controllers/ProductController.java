package kg.buyers.productservice.controllers;

import kg.buyers.productservice.dto.ProductDTO;
import kg.buyers.productservice.entities.Product;
import kg.buyers.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);
        return optionalProduct.map(
                product ->
                        new ResponseEntity<>(product, HttpStatus.OK))
                        .orElseGet(() ->
                                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProductByIdList(@RequestBody List<String> productIdList){
        List<Product> products = new ArrayList<>();
        productIdList.forEach(productId -> {
            products.add(productService.findById(productId).orElse(null));
        });
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody String sellerId) {
        Product savedProduct = productService.initProduct(sellerId);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/bulk")
//    public ResponseEntity<List<Product>> createProducts(@RequestBody List<ProductDTO> productDTOS){
//        List<Product> saved = new ArrayList<>();
//        productDTOS.forEach(productDTO -> {
//            Product product = productService.createProduct(productDTO);
//            saved.add(product);
//        });
//        return new ResponseEntity<>(saved,HttpStatus.CREATED);
//    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable String id){
        Product updatedProduct = productService.updateProduct(product,id);
        if(updatedProduct==null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
