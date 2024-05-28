package kg.buyers.productservice.controllers;

import kg.buyers.productservice.dto.CommentDTO;
import kg.buyers.productservice.entities.Comment;
import kg.buyers.productservice.entities.Review;
import kg.buyers.productservice.repositories.ICommentRepository;
import kg.buyers.productservice.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(commentDTO), HttpStatus.OK);
    }
}
