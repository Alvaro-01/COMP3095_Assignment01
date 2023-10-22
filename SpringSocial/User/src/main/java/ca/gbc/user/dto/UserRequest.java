package ca.gbc.user.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class UserRequest {
    private String name;
    private String msg;


}