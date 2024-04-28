package kg.buyers.productservice.dto;
import kg.buyers.productservice.entities.Comment;
import kg.buyers.productservice.entities.Review;
import lombok.Data;

@Data
public class CommentDTO {
    private String userId;
    private String productId;
    private String type;
    private Review reviewId;
    private Comment commentId;
    private String text;
}
