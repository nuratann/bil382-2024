package kg.buyers.searchservice.services;

import kg.buyers.searchservice.documents.Product;
import kg.buyers.searchservice.dto.ProductDTO;
import kg.buyers.searchservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductDTO productDTO){
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImages(productDTO.getImages());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        product.setOldPrice(productDTO.getOldPrice());
        return productRepository.save(product);
    }

    public Product findById(String id){
        return productRepository.findById(id).orElse(null);
    }



}
