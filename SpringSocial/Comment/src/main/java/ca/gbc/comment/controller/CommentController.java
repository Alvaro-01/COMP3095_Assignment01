package ca.gbc.comment.controller;


import ca.gbc.comment.dto.CommentRequest;
import ca.gbc.comment.dto.CommentResponse;
import ca.gbc.comment.services.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllProducts(){
        return commentService.getAllComments();
    }

    @PutMapping({"/{commentId}"})
    public ResponseEntity<?> updateProduct(@PathVariable("commentId") String commentId,
                                            @RequestBody CommentRequest commentRequest){
        String updatedCommentId = commentService.updateComment(commentId,commentRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/comment/" + updatedCommentId);
        return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{commentId}"})
    public ResponseEntity<?> deleteProduct(@PathVariable("commentId") String commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
