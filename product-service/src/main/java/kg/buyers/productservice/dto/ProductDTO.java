package kg.buyers.productservice.dto;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class ProductDTO {
    private String category;
    private String mediaLinks;//Json String
    //mediaLinks:[mediaLink:{type:"img","url"},mediaLink:{type:"vid","url"}]
    private String sellerId;
    private String title;
    private String description; //HTML code
    @Column(columnDefinition = "TEXT")
    private String options;//Json String
    //options:[option:{title:"цвет",[option:{title:"синий",icon:"url"}]}]
    @Column(columnDefinition = "TEXT")
    private String specs;//Json String
    //specs:[spec:{title:"Емкость аккумулятора",value:"5000Mah"}]
    private Integer stock;
    private Integer deliveryDays;
}
