package ca.gbc.user.services;



import ca.gbc.user.dto.UserRequest;
import ca.gbc.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    void createUser(UserRequest productRequest);

    String updateUser(String productId, UserRequest userRequest);

    void deleteUser(String userId);

    List<UserResponse> getAllUsers();
}
