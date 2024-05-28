package kg.buyers.productservice.controllers;

import kg.buyers.productservice.dto.ReviewDTO;
import kg.buyers.productservice.entities.Review;
import kg.buyers.productservice.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {
    private  final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/")
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO){
        return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatus.CREATED);
    }
}
