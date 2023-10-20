package ca.gbc.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private List<UserLineItemDto> UserLineItemDtoList =
            new ArrayList<UserLineItemDto>();
}
