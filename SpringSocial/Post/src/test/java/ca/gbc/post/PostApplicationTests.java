package ca.gbc.post;


import ca.gbc.post.dto.PostRequest;
import ca.gbc.post.dto.PostResponse;
import ca.gbc.post.model.Post;
import ca.gbc.post.repository.PostRepository;
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
class PostApplicationTests extends AbstractContainerBaseTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    private PostRequest getProductRequest() {
        return PostRequest.builder()
                .name("Jony")
                .msg("Nice")
                .build();
    }

    private List<Post> getProductList() {

        List<Post> products = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        Post comment = Post.builder()
                .id(uuid.toString())
                .name("Jony")
                .msg("Nice")

                .build();
        products.add(comment);

        return products;
    }

    private String convertObjectToString(List<PostResponse> commentList) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(commentList);
    }

    private List<PostResponse> convertStringToObject(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, new TypeReference<List<PostResponse>>() {
        });
    }

    @Test
    void createProduct() throws Exception {

        //Setup
        PostRequest postRequest = getProductRequest();
        String commentRequestJsonString = objectMapper.writeValueAsString(postRequest);

        //Action
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentRequestJsonString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        System.out.println("postRepository.findAll().size()" + postRepository.findAll().size());

        //Verify
        Assertions.assertTrue(postRepository.findAll().size() > 0);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Apple iPad 2023"));
        List<Post> product = mongoTemplate.find(query, Post.class);
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
        postRepository.saveAll(getProductList());

        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/post")
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
        Post savedComment = Post.builder()
                .id(UUID.randomUUID().toString())
                .name("Jacob")
                .msg("I agree")
                .build();

        //Saved product with original price
        postRepository.save(savedComment);

        //Prepare updated product and productRequest
        savedComment.setMsg("I disagree");
        String productRequestString = objectMapper.writeValueAsString(savedComment);


        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/post/" + savedComment.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString));

        //THEN
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedComment.getId()));
        Post storedComment = mongoTemplate.findOne(query, Post.class);

        assertEquals(savedComment.getMsg(), storedComment.getMsg());

    }

    @Test
    void deleteProduct() throws Exception {

        //GIVEN
        Post savedPost = Post.builder()
                .id(UUID.randomUUID().toString())
                .name("Juan")
                .msg("Hello")
                .build();

        postRepository.save(savedPost);

        //WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/post/" + savedPost.getId().toString())
                .contentType(MediaType.APPLICATION_JSON));

        //THEN
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        response.andDo(MockMvcResultHandlers.print());

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(savedPost.getId()));
        Long productCount = mongoTemplate.count(query, Post.class);

        assertEquals(0, productCount);

    }

}
