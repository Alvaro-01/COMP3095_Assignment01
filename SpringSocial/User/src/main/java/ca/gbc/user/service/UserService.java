package ca.gbc.user.service;


import ca.gbc.user.dto.UserRequest;

public interface UserService {
    void placeUser(UserRequest postRequest);
}
