package test;

import dto.PostCreationRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostTest {


    @Test
    public void successfulPostCreation() {
        String token = Helper.userRegistration();
        PostCreationRequest postCreationRequest = new PostCreationRequest();

        postCreationRequest.setTitle(Helper.generateRandomTitle());
        postCreationRequest.setDescription(Helper.generateRandomDescription());
        postCreationRequest.setBody(Helper.generateRandomContent());

        postCreationRequest.setImageUrl("https://gb.ru/blog/wp-content/uploads/2022/11/shutterstock_1023888442.jpg");

        Response postCreateResponse = BaseTest.postRequestWithToken(token, "/api/posts", 200, postCreationRequest);
        assertNotNull(postCreateResponse.jsonPath().getString("id"), "Post ID should not be null");
        assertEquals(postCreationRequest.getTitle(), postCreateResponse.jsonPath().getString("title"), "Post title does not match");
    }

    @Test
    public void NoPostCreationWithoutAuthentication() {

        PostCreationRequest postCreationRequest = new PostCreationRequest();

        postCreationRequest.setTitle(Helper.generateRandomTitle());
        postCreationRequest.setDescription(Helper.generateRandomDescription());
        postCreationRequest.setBody(Helper.generateRandomContent());

        postCreationRequest.setImageUrl("https://gb.ru/blog/wp-content/uploads/2022/11/shutterstock_1023888442.jpg");
        Response postCreateResponse = BaseTest.postRequestWithoutToken("/api/posts", 401, postCreationRequest);

        assertTrue(postCreateResponse.path("message").equals("Authentication failed: Full authentication is required to access this resource"));
        assertTrue(postCreateResponse.path("httpStatus").equals("UNAUTHORIZED"));
    }

    @Test
    public void NoPostCreationWithoutTitle() {
        String token = Helper.userRegistration();
        PostCreationRequest postCreationRequest = new PostCreationRequest();

        postCreationRequest.setDescription(Helper.generateRandomDescription());
        postCreationRequest.setBody(Helper.generateRandomContent());


        Response postCreateResponse = BaseTest.postRequestWithToken(token, "/api/posts", 400, postCreationRequest);

        String errorMessage = postCreateResponse.jsonPath().getString("title[0]");
        assertTrue(errorMessage.equals("Title can not be empty!"), "Expected error message not found");

    }
}
