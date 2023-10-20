package ca.gbc.post.controller;

import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeComment(@RequestBody PostRequest request){
        postService.placePost(request);
        return "Order Placed Successfully";
    }
}
