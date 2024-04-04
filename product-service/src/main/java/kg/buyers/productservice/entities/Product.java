package kg.buyers.productservice.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "seller_id", referencedColumnName = "id")
//    private Seller seller;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category category;

    private Integer stock;
    private String article;
    private Double price;
    private Double oldPrice;
    private String images;
}
