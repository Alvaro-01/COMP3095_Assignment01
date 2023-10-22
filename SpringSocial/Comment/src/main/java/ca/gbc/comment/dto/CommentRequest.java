package ca.gbc.comment.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class CommentRequest {
    private String name;
    private String msg;


}