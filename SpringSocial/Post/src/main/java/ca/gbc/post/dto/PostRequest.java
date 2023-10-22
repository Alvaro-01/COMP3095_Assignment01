package ca.gbc.post.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class PostRequest {
    private String name;
    private String msg;


}