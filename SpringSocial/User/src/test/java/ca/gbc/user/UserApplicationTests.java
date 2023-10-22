package ca.gbc.user;


import ca.gbc.user.dto.UserRequest;
import ca.gbc.user.dto.UserResponse;
import ca.gbc.user.model.User;
import ca.gbc.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class UserApplicationTests extends AbstractContainerBaseTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    private UserRequest getProductRequest() {
        return UserRequest.builder()
                .name("Jony")
                .msg("Nice")
                .build();
    }

    private List<User> getProductList() {

        List<User> products = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        User comment = User.builder()
                .id(uuid.toString())
                .name("Jony")
                .msg("Nice")

                .build();
        products.add(comment);

        return products;
    }

    private String convertObjectToString(List<UserResponse> commentList) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(commentList);
    }

    private List<UserResponse> convertStringToObject(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<List<UserResponse>>() {
        });
    }

    @Test
    void createProduct() throws Exception {

        //Setup
        UserRequest commentRequest = getProductRequest();
        String commentRequestJsonString = objectMapper.writeValueAsString(commentRequest);

        //Action
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentRequestJsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        System.out.println("productRepository.findAll().size()" + userRepository.findAll().size());

        //Verify
        Assertions.assertTrue(userRepository.findAll().size() > 0);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Apple iPad 2023"));
        List<User> product = mongoTemplate.find(query, User.class);
        Assertions.assertTrue(product.size() > 0);

    }

    /***********************
     * BDD - Behavior Driven Development
     * // Given - Setup
     * // When - Action
     * // Then - Verify
     *************************/

    @Test
    void getAllProducts() throws Exception {

        //GIVEN
        userRepository.saveAll(getProductList());

        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comment")
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(MockMvcResultHandlers.print());

        MvcResult result = response.andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNodes = new ObjectMapper().readTree(jsonResponse);

        int actualSize = jsonNodes.size();
        int expectedSize = getProductList().size();

        Assertions.assertEquals(expectedSize, actualSize);

    }

    @Test
    void updateProducts() throws Exception {

        //GIVEN
        User savedComment = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Jacob")
                .msg("I agree")
                .build();

        //Saved product with original price
        userRepository.save(savedComment);

        //Prepare updated product and productRequest
        savedComment.setMsg("I disagree");
        String productRequestString = objectMapper.writeValueAsString(savedComment);


        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/user/" + savedComment.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //THEN
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedComment.getId()));
        User storedComment = mongoTemplate.findOne(query, User.class);

        assertEquals(savedComment.getMsg(), storedComment.getMsg());

    }

    @Test
    void deleteProduct() throws Exception {

        //GIVEN
        User savedComment = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Juan")
                .msg("Hello")
                .build();

        userRepository.save(savedComment);

        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/user/" + savedComment.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedComment.getId()));
        Long productCount = mongoTemplate.count(query, User.class);

        assertEquals(0, productCount);

    }

}
