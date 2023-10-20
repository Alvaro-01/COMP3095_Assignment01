package ca.gbc.user.service;

import ca.gbc.user.dto.UserLineItemDto;
import ca.gbc.user.dto.UserRequest;
import ca.gbc.user.model.User;
import ca.gbc.user.model.UserLineItem;
import ca.gbc.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void placeUser(UserRequest userRequest) {
        User user = new User();
        user.setPostMessage(UUID.randomUUID().toString());

        List<UserLineItem> userLineItems =
                userRequest.getUserLineItemDtoList().stream().map(this::mapToDo).toList();
        user.setPostLineItemList(userLineItems);
        userRepository.save(user);
    }

    private UserLineItem mapToDo(UserLineItemDto userLineItemDto){
        UserLineItem userLineItem = new UserLineItem();
        userLineItem.setPostMessage(userLineItemDto.getPostMessage());
        return userLineItem;
    }

}
