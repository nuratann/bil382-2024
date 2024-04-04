package kg.buyers.recommendationservice.controllers;

import kg.buyers.recommendationservice.entities.Product;
import kg.buyers.recommendationservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/recommendations")
public class RecommendationsController {
    private final ProductRepository productRepository;

    @Autowired
    public RecommendationsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/byUser/{userId}")
    public List<Product> byUserId(@PathVariable Long userId){
        return productRepository.findAll();
        //TODO: Сделать нормальный алгоритм рекомендаций по ID пользователя
    }

    @GetMapping("/byCategory/{category}")
    public List<Product> byCategory(@PathVariable Long category){
        return productRepository.findAll();
        //TODO: Сделать нормальный алгоритм рекомендаций по категории
    }

}
