package test;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteUserTest {

    @Test
    public void successfulUserDeleteByAdmin() {

        String token = Helper.adminRegistration();

        String newUserToken = Helper.userRegistration();
        Response newUserResponse = BaseTest.getRequest(newUserToken, "/api/me", 200);
        String userId = newUserResponse.jsonPath().getString("id");


        String endPoint = "/api/users/" + userId;
        Response response = BaseTest.deleteRequest(token, endPoint, 200);
        assertEquals(200, response.getStatusCode());

        Response allUsersResponse = BaseTest.getRequest(token, "/api/users", 200);
        List<String> ids = allUsersResponse.jsonPath().getList("id");
        assertFalse(ids.contains(userId));



    }

    @Test
    public void noUserDeleteByUser() {

        String token = Helper.userRegistration();

        String newUserToken = Helper.userRegistration();
        Response newUserResponse = BaseTest.getRequest(newUserToken, "/api/me", 200);
        String userId = newUserResponse.jsonPath().getString("id");


        String endPoint = "/api/users/" + userId;
        Response response = BaseTest.deleteRequest(token, endPoint, 401);

        String expectedMessage = "You don't have permission to delete users";
        String actualMessage = response.getBody().asString();
        assertTrue(actualMessage.contains(expectedMessage), "The expected message not found");

    }

    @Test
    public void noUserDeleteWithEmptyId() {

        String token = Helper.adminRegistration();

        String userId = " ";


        String endPoint = "/api/users/" + userId;
        Response response = BaseTest.deleteRequest(token, endPoint, 400);
        String expectedMessage = "ID can not be empty";
        String actualMessage = response.getBody().asString();
        assertTrue(actualMessage.contains(expectedMessage), "The expected message not found");

    }
}
