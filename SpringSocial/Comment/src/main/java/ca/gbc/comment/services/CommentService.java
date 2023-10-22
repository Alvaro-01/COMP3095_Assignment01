package ca.gbc.comment.services;

import ca.gbc.comment.dto.CommentRequest;
import ca.gbc.comment.dto.CommentResponse;


import java.util.List;

public interface CommentService {

    void createComment(CommentRequest productRequest);

    String updateComment(String commentId, CommentRequest commentRequest);

    void deleteComment(String commentId);

    List<CommentResponse> getAllComments();
}
