package test;

import com.github.javafaker.Faker;
import dto.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    static Faker faker = new Faker();

    public static String userRegistration() {

            String email = faker.internet().emailAddress();
            String password = faker.internet().password(8, 20);
            String role = "user";

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(email);
            registerRequest.setPassword(password);
            registerRequest.setConfirmPassword(password);
            registerRequest.setRole(role);

      Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        String token = response.path("accessToken");

        return token;
}


    public static RegisterResponse userRegistrationFullResponse() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "user";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);

        Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        return response.as(RegisterResponse.class);
    }
    public static String adminRegistration() {

        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "admin";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);

        Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        String token = response.path("accessToken");

        return token;
    }

    public static String userLogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 200, loginRequest);
        return loginResponse.path("accessToken");
    }


    public static String generateRandomTitle() {
        String title = faker.lorem().sentence(5);
        return title.substring(0, Math.min(title.length(), 40));
    }

    public static String generateRandomDescription() {
        String description = faker.lorem().sentence(10);
        return description.substring(0, Math.min(description.length(), 100));
    }

    public static String generateRandomContent() {
        String content = faker.lorem().paragraph(20);
        return content.substring(0, Math.min(content.length(), 1000));
    }

    public static Map<String, String> createPostAndGetIds(String token) {
        PostCreationRequest postCreationRequest = new PostCreationRequest();

        postCreationRequest.setTitle(generateRandomTitle());
        postCreationRequest.setDescription(generateRandomDescription());
        postCreationRequest.setBody(generateRandomContent());


        Response response = BaseTest.postRequestWithToken(token, "/api/posts", 201, postCreationRequest);
        String postId = response.jsonPath().getString("id");
        String userId = response.jsonPath().getString("userId");

        Map<String, String> ids = new HashMap<>();
        ids.put("postId", postId);
        ids.put("userId", userId);

        return ids;
    }
}
