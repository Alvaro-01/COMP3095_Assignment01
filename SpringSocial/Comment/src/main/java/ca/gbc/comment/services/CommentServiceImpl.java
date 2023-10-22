package ca.gbc.comment.services;


import ca.gbc.comment.dto.CommentRequest;
import ca.gbc.comment.dto.CommentResponse;
import ca.gbc.comment.model.Comment;
import ca.gbc.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public void createComment(CommentRequest commentRequest) {
        log.info("Creating a new comment {}",commentRequest.getName());

        Comment comment = Comment.builder()
                .name(commentRequest.getName())
                .msg(commentRequest.getMsg())
                .build();
        commentRepository.save(comment);

        log.info("Comment {} is saved",comment.getId());
    }

    @Override
    public String updateComment(String productId, CommentRequest commentRequest) {
        log.info("updating comment with Id {}",productId);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(productId));
        Comment comment = mongoTemplate.findOne(query,Comment.class);

        if(comment != null){
            comment.setName(commentRequest.getName());
            comment.setMsg(commentRequest.getMsg());


            log.info("Comment {} is updated",comment.getId());
            return commentRepository.save(comment).getId();

        }

        return productId.toString();

    }

    @Override
    public void deleteComment(String commentId) {
        log.info("Product {} is deleted", commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponse> getAllComments() {
        log.info("Returning list of products");
        List<Comment> products = commentRepository.findAll();
        return products.stream().map(this::maptoCommentsResponse).toList();
    }

    private CommentResponse maptoCommentsResponse(Comment product){
        return  CommentResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .msg(product.getMsg())
                .build();
    }
}
