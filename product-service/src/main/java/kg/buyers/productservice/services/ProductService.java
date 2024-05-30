package kg.buyers.productservice.services;
import jakarta.persistence.EntityNotFoundException;
import kg.buyers.productservice.dto.ProductDTO;
import kg.buyers.productservice.entities.Category;
import kg.buyers.productservice.entities.Product;
import kg.buyers.productservice.repositories.ICategoryRepository;
import kg.buyers.productservice.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final IProductRepository iProductRepository;
    private final ICategoryRepository iCategoryRepository;
    @Autowired
    public ProductService(IProductRepository iProductRepository, ICategoryRepository iCategoryRepository){
        this.iProductRepository=iProductRepository;
        this.iCategoryRepository = iCategoryRepository;
    }


    @Transactional
    public Product initProduct(String sellerId){
        Product product = Product.builder()
                .sellerId(sellerId)
                .category(iCategoryRepository.findCategoryByName("Общая").orElse(null))
                .mediaLinks("[]")
                .title("")
                .description("")
                .options("[{\"title\": \"\", \"count\": \"\", \"price\": \"\"}]")
                .specs("[]")
                .stock(0)
                .deliveryDays(0)
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
                .isActive(false)
                .price(0.0)
                .oldPrice(0.0)
                .weight(0.0)
                .orders(0)
                .rating(0.0f)
                .reviews(new ArrayList<>())
                .promotions("[]")
                .build();
        return iProductRepository.save(product);
    }


    @Transactional
    public Product updateProduct(Product new_product, String id) {
        Product product = iProductRepository.findById(id).orElse(null);
        if(product==null)return null;
        new_product.setActive(true);
        return iProductRepository.save(new_product);
    }

    public List<String> getCategoryPath(Integer categoryId) {
        List<String> path = new ArrayList<>();
        Category category = iCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        buildCategoryPath(category, path);
        Collections.reverse(path);  // Оборачиваем список для получения пути от корневой категории до текущей
        return path;
    }

    private void buildCategoryPath(Category category, List<String> path) {
        path.add(category.getName());
        if (category.getParent() != null) {
            buildCategoryPath(iCategoryRepository.findById(category.getParent()).orElse(null), path);
        }
    }

    public Optional<Product> findById(String id){
        return iProductRepository.findById(id);
    }

    public void deleteProduct(String id){

    }
}
