package ca.gbc.post.controller;



import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.dto.PostResponse;
import ca.gbc.post.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllProducts(){
        return postService.getAllPosts();
    }

    @PutMapping({"/{postId}"})
    public ResponseEntity<?> updateProduct(@PathVariable("postId") String commentId,
                                            @RequestBody PostRequest commentRequest){
        String updatedCommentId = postService.updatePost(commentId,commentRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/post/" + updatedCommentId);
        return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{postId}"})
    public ResponseEntity<?> deleteProduct(@PathVariable("postId") String postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
