package ca.gbc.post.services;



import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.dto.PostResponse;
import ca.gbc.post.model.Post;
import ca.gbc.post.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public void createPost(PostRequest postRequest) {
        log.info("Creating a new comment {}",postRequest.getName());

        Post comment = Post.builder()
                .name(postRequest.getName())
                .msg(postRequest.getMsg())
                .build();
        postRepository.save(comment);

        log.info("Post {} is saved",comment.getId());
    }

    @Override
    public String updatePost(String postId, PostRequest postRequest) {
        log.info("updating Post with Id {}",postId);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(postId));
        Post comment = mongoTemplate.findOne(query,Post.class);

        if(comment != null){
            comment.setName(postRequest.getName());
            comment.setMsg(postRequest.getMsg());


            log.info("Comment {} is updated",comment.getId());
            return postRepository.save(comment).getId();

        }

        return postId.toString();

    }

    @Override
    public void deletePost(String commentId) {
        log.info("Post {} is deleted", commentId);
        postRepository.deleteById(commentId);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        log.info("Returning list of products");
        List<Post> products = postRepository.findAll();
        return products.stream().map(this::maptoPostsResponse).toList();
    }

    private PostResponse maptoPostsResponse(Post product){
        return  PostResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .msg(product.getMsg())
                .build();
    }
}
