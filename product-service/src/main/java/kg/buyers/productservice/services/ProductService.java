package kg.buyers.productservice.services;
import kg.buyers.productservice.dto.ProductDTO;
import kg.buyers.productservice.entities.Product;
import kg.buyers.productservice.repositories.ICategoryRepository;
import kg.buyers.productservice.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public Optional<Product> findById(String id){
        return iProductRepository.findById(id);
    }

    public void deleteProduct(String id){

    }
}
