package kg.buyers.elasticservice.entities;

import jakarta.json.Json;
import lombok.Data;

import java.util.List;

@Data
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
    private boolean is_premium_seller;
    private int reviews;
    private String delivery_date;
}