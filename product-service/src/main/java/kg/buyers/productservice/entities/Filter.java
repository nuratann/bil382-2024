package kg.buyers.productservice.entities;

import jakarta.persistence.ManyToMany;

import java.util.Set;

public class Filter {
    private String title;
    private Set<String> options;
    @ManyToMany
    private Set<Category> categories;
}
