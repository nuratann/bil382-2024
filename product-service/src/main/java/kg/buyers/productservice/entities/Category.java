package kg.buyers.productservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer parent;
    private Integer productCount;
    @ManyToMany
    private Set<Brand> brands;
    @Column(columnDefinition = "TEXT")
    private String filters;
}
