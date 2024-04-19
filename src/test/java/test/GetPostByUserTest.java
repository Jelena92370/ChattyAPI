package test;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetPostByUserTest {

    @Test
    public void successfulPostListByUser() {


            String token = Helper.userRegistration();

            Map<String, String> post1 = Helper.createPostAndGetIds(token);
            Map<String, String> post2 = Helper.createPostAndGetIds(token);

            String userId = post1.get("userId");


            String endPoint = String.format("/api/users/%s/posts?skip=%d&limit=%d", userId, 0, 10);
            System.out.println("Endpoint: " + endPoint);

            Response response = BaseTest.getRequest(token, endPoint, 200);


            List<String> postIds = response.jsonPath().getList("id");
            assertTrue(postIds.contains(post1.get("postId")), "The list of posts should contain the first postId");
            assertTrue(postIds.contains(post2.get("postId")), "The list of posts should contain the second postId");

        List<String> userIds = response.jsonPath().getList("posts.user.id");
        for (String retrievedUserId : userIds) {
            assertEquals(userId, retrievedUserId, "Each post should belong to the userId");
        }
    }

    @Test
    public void EmptyPostListWithNonExistentUserId() {

        String token = Helper.userRegistration();
        String nonExistentUserId = UUID.randomUUID().toString();

        String endPoint = String.format("/api/users/%s/posts?skip=%d&limit=%d", nonExistentUserId, 0, 10);
        Response response = BaseTest.getRequest(token, endPoint, 200);
        List<List> posts = response.jsonPath().getList("");
        assertTrue(posts.isEmpty(), "Expected an empty list of posts");
    }

    @Test
    public void noPostsListWithEmptyUserId() {
        String token = Helper.userRegistration();
        String emptyUserId = "";

        String endPoint = String.format("/api/users/%s/posts?skip=%d&limit=%d", emptyUserId, 0, 10);
        Response response = BaseTest.getRequest(token, endPoint, 400);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Bad Request"), "The response should contain 'Bad Request'.");

            }

    @Test
    public void noPostsListWithWrongUserIdType() {
        String token = Helper.userRegistration();
        String wrongUserId = "ID";

        String endPoint = String.format("/api/users/%s/posts?skip=%d&limit=%d", wrongUserId, 0, 10);
        Response response = BaseTest.getRequest(token, endPoint, 400);

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Bad Request"), "The response should contain 'Bad Request'.");

    }
    }


