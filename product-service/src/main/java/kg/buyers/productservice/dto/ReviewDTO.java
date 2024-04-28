package kg.buyers.productservice.dto;
import kg.buyers.productservice.entities.Product;
import lombok.Data;

@Data
public class ReviewDTO {
    private Product productId;
    private String reviewerId;//user_id
    private String orderId;
    private Short grade;
    private String options;
    private String text;
    private String images;
}
