package ca.gbc.user.controller;


import ca.gbc.user.dto.UserRequest;
import ca.gbc.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeUser(@RequestBody UserRequest request){
        userService.placeUser(request);
        return "User Placed Successfully";
    }
}
