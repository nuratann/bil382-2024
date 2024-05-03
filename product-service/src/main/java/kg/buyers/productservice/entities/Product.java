package kg.buyers.productservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Category category;
    private String sellerId;
    @Column(columnDefinition = "TEXT")
    private String mediaLinks;//Json String
    //mediaLinks:[mediaLink:{type:"img","url"},mediaLink:{type:"vid","url"}]
    private String title;
    private String description; //HTML code
    private Float rating;
    private Integer orders;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @Column(columnDefinition = "TEXT")
    private String options;//Json String
    //options:[option:{title:"цвет",[option:{title:"синий",icon:"url"}]}]
    @Column(columnDefinition = "TEXT")
    private String specs;//Json String
    //specs:[spec:{title:"Емкость аккумулятора",value:"5000Mah"}]
    private Integer stock;
    @Column(columnDefinition = "TEXT")
    private String promotions;//Json String
    //promotions:[promotion:{type:"points for review",title:"баллы за отзыв",value:"450"}]
    private Integer deliveryDays;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
