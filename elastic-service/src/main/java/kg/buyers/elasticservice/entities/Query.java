package kg.buyers.elasticservice.entities;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Query {
    private String query;
    //общие фильтры
    private String category;
    private Set<String> brands;
    private Range priceRange;
    private boolean premium;
    private boolean sale;
    private Set<String> colors;
    private Set<String> sellers;
    private boolean discounted;
    private Range delivery;
    private boolean original;
    private boolean pointsForReview;
    private Set<String> madeCountries;
    private Range rating;
    private Set<String> types;
    private boolean bestSellers;
    //extend фильтры для каждой категории свои
    private List<Filter> filters;
}
