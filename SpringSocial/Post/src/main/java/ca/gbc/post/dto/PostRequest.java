package ca.gbc.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private List<PostLineItemDto> postLineItemDtoList =
            new ArrayList<PostLineItemDto>();
}
