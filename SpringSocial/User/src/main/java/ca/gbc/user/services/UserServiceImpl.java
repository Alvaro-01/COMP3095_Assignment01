package ca.gbc.user.services;



import ca.gbc.user.dto.UserRequest;
import ca.gbc.user.dto.UserResponse;
import ca.gbc.user.model.User;
import ca.gbc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    @Override
    public void createUser(UserRequest userRequest) {
        log.info("Creating a new comment {}",userRequest.getName());

        UserService comment = UserService.builder()
                .name(userRequest.getName())
                .msg(userRequest.getMsg())
                .build();
        userRepository.save(comment);

        log.info("Comment {} is saved",comment.getId());
    }

    @Override
    public String updateUser(String productId, UserRequest commentRequest) {
        log.info("updating comment with Id {}",productId);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(productId));
        User comment = mongoTemplate.findOne(query,User.class);

        if(comment != null){
            comment.setName(commentRequest.getName());
            comment.setMsg(commentRequest.getMsg());


            log.info("Comment {} is updated",comment.getId());
            return userRepository.save(comment).getId();

        }

        return productId.toString();

    }

    @Override
    public void deleteUser(String userIdId) {
        log.info("Product {} is deleted", userIdId);
        userRepository.deleteById(userIdId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Returning list of products");
        List<User> products = userRepository.findAll();
        return products.stream().map(this::maptoUserResponse).toList();
    }

    private UserResponse maptoUserResponse(User product){
        return  UserResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .msg(product.getMsg())
                .build();
    }
}
