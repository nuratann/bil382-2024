package kg.buyers.productservice.services;
import kg.buyers.productservice.dto.CommentDTO;
import kg.buyers.productservice.entities.Comment;
import kg.buyers.productservice.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    private final ICommentRepository iCommentRepository;


    @Autowired
    public CommentService(ICommentRepository iCommentRepository) {
        this.iCommentRepository = iCommentRepository;
    }

    @Transactional
    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .userId(commentDTO.getUserId())
                .productId(commentDTO.getProductId())
                .type(commentDTO.getType())
                .review(commentDTO.getReviewId())
                .comment(commentDTO.getCommentId())
                .text(commentDTO.getText())
                .likes(0)
                .dislikes(0)
                .commentDate(Timestamp.valueOf(LocalDateTime.now()))
                .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return iCommentRepository.save(comment);
    }

    public void incrementLikes(String id){
        Comment comment = iCommentRepository.findById(id).orElse(null);
        if(comment!=null) {
            comment.setLikes(comment.getLikes()+1);
            iCommentRepository.save(comment);
        }
    }

    public void incrementDislikes(String id){
        Comment comment = iCommentRepository.findById(id).orElse(null);
        if(comment!=null) {
            comment.setDislikes(comment.getDislikes()+1);
            iCommentRepository.save(comment);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Comment> findById(String id) {
        return iCommentRepository.findById(id);
    }

    @Transactional
    public Comment updateComment(CommentDTO commentDTO, String id) {
        Comment comment = iCommentRepository.findById(id).orElse(null);
        if(comment==null) return null;
        comment.setText(commentDTO.getText());
        return iCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(String id) {
        iCommentRepository.deleteById(id);
    }
}
