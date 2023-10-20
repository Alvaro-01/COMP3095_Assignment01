package ca.gbc.post.service;

import ca.gbc.post.dto.PostLineItemDto;
import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.model.Post;
import ca.gbc.post.model.PostLineItem;
import ca.gbc.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Override
    public void placePost(PostRequest postRequest) {
        Post post = new Post();
        post.setPostMessage(UUID.randomUUID().toString());

        List<PostLineItem> postLineItems =
                postRequest.getPostLineItemDtoList().stream().map(this::mapToDo).toList();
        post.setPostLineItemList(postLineItems);
        postRepository.save(post);
    }

    private PostLineItem mapToDo(PostLineItemDto postLineItemDto){
        PostLineItem postLineItem = new PostLineItem();
        postLineItem.setPostMessage(postLineItemDto.getPostMessage());
        return postLineItem;
    }

}
