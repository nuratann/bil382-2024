package kg.buyers.elasticservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String img;
    private String title;
    private String description;
    private String suggest;
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

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.img = product.getImg();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.suggest = product.getDescription();
        this.rating = product.getRating();
        this.category = product.getCategory();
        this.orders = product.getOrders();
        this.sells = product.getSells();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.old_price = product.getOld_price();
        this.seller = product.getSeller();
        this.premium_seller = product.isPremium_seller();
        this.reviews = product.getReviews();
        this.delivery_date = product.getDelivery_date();
    }
}