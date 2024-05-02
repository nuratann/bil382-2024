package kg.buyers.productservice.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String logoUrl;
    private String description;
    @ManyToMany
    private Set<Category> categories;
}
