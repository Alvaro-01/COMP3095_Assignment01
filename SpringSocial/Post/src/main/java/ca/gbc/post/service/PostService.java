package ca.gbc.post.service;

import ca.gbc.post.dto.PostRequest;

public interface PostService {
    void placePost(PostRequest postRequest);
}
