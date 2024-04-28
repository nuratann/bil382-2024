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
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String productId;
    private String type;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment comment;
    private String text;
    private Integer likes;
    private Integer dislikes;
    private Timestamp commentDate;
    private Timestamp updatedDate;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
