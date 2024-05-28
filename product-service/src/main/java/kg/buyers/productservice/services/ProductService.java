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
    public Product createProduct(ProductDTO productDTO){
        Product product = Product.builder()
                .sellerId(productDTO.getSellerId())
                .category(iCategoryRepository.findCategoryByName(productDTO.getCategory()).orElse(null))
                .mediaLinks(productDTO.getMediaLinks())
                .title(productDTO.getTitle())
                .description(productDTO.getDescription())
                .options(productDTO.getOptions())
                .specs(productDTO.getSpecs())
                .stock(productDTO.getStock())
                .deliveryDays(productDTO.getDeliveryDays())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return iProductRepository.save(product);
    }

    @Transactional
    public Product updateProduct(ProductDTO productDTO, String id) {
        Product product = iProductRepository.findById(id).orElse(null);
        if(product==null)return null;
        product.setCategory(iCategoryRepository.findCategoryByName(productDTO.getCategory()).orElse(null));
        product.setMediaLinks(productDTO.getMediaLinks());
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setOptions(productDTO.getOptions());
        product.setSpecs(productDTO.getSpecs());
        product.setStock(productDTO.getStock());
        product.setDeliveryDays(productDTO.getDeliveryDays());
        product.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        return iProductRepository.save(product);
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
