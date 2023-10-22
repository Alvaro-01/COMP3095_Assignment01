package ca.gbc.post.services;


import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.dto.PostResponse;

import java.util.List;

public interface PostService {

    void createPost(PostRequest productRequest);

    String updatePost(String postId, PostRequest postRequest);

    void deletePost(String commentId);

    List<PostResponse> getAllPosts();
}
