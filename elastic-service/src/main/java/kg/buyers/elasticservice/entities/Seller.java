package kg.buyers.elasticservice.entities;

import lombok.Data;

@Data
public class Seller {
    private String id;
    private String title;
    private String img;
    private Float rating;
    private Integer orders;
    private Boolean premium;
}
