package ca.gbc.comment.service;

import ca.gbc.comment.dto.CommentLineItemDto;
import ca.gbc.comment.dto.CommentRequest;
import ca.gbc.comment.model.Comment;
import ca.gbc.comment.model.CommentLineItem;
import ca.gbc.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements  CommentService{

    private final CommentRepository commentRepository;

    @Override
    public void placeComment(CommentRequest orderRequest) {
        Comment comment = new Comment();
        comment.setComment(UUID.randomUUID().toString());
        List<CommentLineItem> commentLineItems =
        orderRequest.getCommentLineItemDtoList().stream().map(this::mapToDo).toList();
        comment.setOrderLineItemList(commentLineItems);
    }

    private CommentLineItem mapToDo(CommentLineItemDto commentLineItemDto){
        CommentLineItem commentLineItem = new CommentLineItem();
        commentLineItem.setComment(commentLineItemDto.getComment());
        return commentLineItem;
    }


}
