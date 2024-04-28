package kg.buyers.productservice.services;

import kg.buyers.productservice.dto.ReviewDTO;
import kg.buyers.productservice.entities.Review;
import kg.buyers.productservice.repositories.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {
    private final IReviewRepository iReviewRepository;


    @Autowired
    public ReviewService(IReviewRepository iReviewRepository) {
        this.iReviewRepository = iReviewRepository;
    }

    @Transactional
    public Review createReview(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .product(reviewDTO.getProductId())
                .reviewerId(reviewDTO.getReviewerId())
                .orderId(reviewDTO.getOrderId())
                .grade(reviewDTO.getGrade())
                .options(reviewDTO.getOptions())
                .text(reviewDTO.getText())
                .images(reviewDTO.getImages())
                .reviewDate(Timestamp.valueOf(LocalDateTime.now()))
                .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
                .likes(0)
                .dislikes(0)
                .build();
        return iReviewRepository.save(review);
    }

    public void incrementLikes(String id){
        Review review = iReviewRepository.findById(id).orElse(null);
        if(review!=null) {
            review.setLikes(review.getLikes()+1);
            iReviewRepository.save(review);
        }
    }

    public void incrementDislikes(String id){
        Review review = iReviewRepository.findById(id).orElse(null);
        if(review!=null) {
            review.setDislikes(review.getDislikes()+1);
            iReviewRepository.save(review);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Review> findById(String id) {
        return iReviewRepository.findById(id);
    }

    @Transactional
    public Review updateReview(ReviewDTO reviewDTO, String id) {
        Review review = iReviewRepository.findById(id).orElse(null);
        if(review==null) return null;
        review.setGrade(reviewDTO.getGrade());
        review.setText(reviewDTO.getText());
        review.setImages(reviewDTO.getImages());
        return iReviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(String id) {
        iReviewRepository.deleteById(id);
    }

}
