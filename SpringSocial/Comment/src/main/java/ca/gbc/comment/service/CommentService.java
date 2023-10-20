package ca.gbc.comment.service;

import ca.gbc.comment.dto.CommentRequest;

public interface CommentService {
    void placeComment(CommentRequest orderRequest);
}
