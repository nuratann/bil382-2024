package kg.buyers.elasticservice.entities;

import jakarta.json.Json;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Product {
    //если меняешь схему тут не забудь поменять и в es config
    private String id;
    private String title;
    private String description;
    private Float rating;
    private String category;
    private Integer orders;
    private String brand;
    private Boolean premium;
    private Boolean sale;
    private Set<String> colors;
    private String seller;
    private Boolean discounted;
    private Integer delivery;
    private Boolean original;
    private Boolean pointsForReview;
    private String madeCountry;
    private String type;
    private Boolean bestSeller;
    private Integer quantity;
    private Float price;
    private Float old_price;

}