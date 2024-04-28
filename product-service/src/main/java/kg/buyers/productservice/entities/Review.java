package kg.buyers.productservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "reviews")
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String reviewerId;//user_id
    private String orderId;
    private Short grade;
    private String options;
    private String text;
    @Column(columnDefinition = "TEXT")
    private String images;
    private Timestamp reviewDate;
    private Timestamp updatedDate;
    private Integer likes;
    private Integer dislikes;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
