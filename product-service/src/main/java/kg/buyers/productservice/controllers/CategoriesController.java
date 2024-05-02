package kg.buyers.productservice.controllers;

import kg.buyers.productservice.entities.Category;
import kg.buyers.productservice.repositories.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {
    public final ICategoryRepository iCategoryRepository;

    @Autowired
    public CategoriesController(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(iCategoryRepository.findAll(), HttpStatus.OK);
    }
}
