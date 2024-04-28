package kg.buyers.elasticservice.entities;

import jakarta.json.Json;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Product {
    private String id;
    private String img;
    private String title;
    private String description;
    //private Json specs;
    private float rating;
    private String category;
    private int orders;
    private List<String> sells;
    private int quantity;
    private float price;
    private float old_price;
    private String seller;
    private boolean premium_seller;
    private int reviews;
    private String delivery_date;

    public Product(ProductDTO productDTO) {
        this.id = productDTO.getId();
        this.img = productDTO.getImg();
        this.title = productDTO.getTitle();
        this.description = productDTO.getDescription();
        this.rating = productDTO.getRating();
        this.category = productDTO.getCategory();
        this.orders = productDTO.getOrders();
        this.sells = productDTO.getSells();
        this.quantity = productDTO.getQuantity();
        this.price = productDTO.getPrice();
        this.old_price = productDTO.getOld_price();
        this.seller = productDTO.getSeller();
        this.premium_seller = productDTO.isPremium_seller();
        this.reviews = productDTO.getReviews();
        this.delivery_date = productDTO.getDelivery_date();
    }
}