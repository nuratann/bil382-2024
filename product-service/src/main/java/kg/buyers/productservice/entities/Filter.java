package kg.buyers.productservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ElementCollection
    private Set<String> options;
    @ManyToMany
    private Set<Category> categories;
}
