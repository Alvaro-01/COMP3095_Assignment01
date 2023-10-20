package ca.gbc.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private List<CommentLineItemDto> commentLineItemDtoList =
            new ArrayList<CommentLineItemDto>();
}
