package kg.buyers.productservice.controllers;

import kg.buyers.productservice.entities.Category;
import kg.buyers.productservice.repositories.ICategoryRepository;
import kg.buyers.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {
    public final ICategoryRepository iCategoryRepository;
    public final ProductService productService;
    @Autowired
    public CategoriesController(ICategoryRepository iCategoryRepository, ProductService productService) {
        this.iCategoryRepository = iCategoryRepository;
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(iCategoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<List<String>> getCategoryPath(@PathVariable Integer id) {
        List<String> path = productService.getCategoryPath(id);
        return ResponseEntity.ok(path);
    }
}
