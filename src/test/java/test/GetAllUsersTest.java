package test;

import dto.UserResponse;
import dto.UsersResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class GetAllUsersTest {
    @Test
    public void successfulGettingAllUsersList() {
        String token = Helper.adminRegistration();
        Response response = BaseTest.getRequest(token, "/api/users", 200);


        List<UserResponse> users = response.jsonPath().getList("", UserResponse.class);


        assertFalse(users.isEmpty(), "The list of user IDs should not be empty");


        int expectedNumberOfUsers = 10;
        assertEquals(expectedNumberOfUsers, users.size(), "The number of users should match the expected number");
    }

    @Test
    public void notGettingAllUsersListAsUser() {
        String token = Helper.userRegistration();
        Response response = BaseTest.getRequest(token, "/api/users", 401);
        String errorMessage = response.jsonPath().getString("message");
        assertEquals("You don't have permission to get users", errorMessage, "Error message should match");


    }

    @Test
    public void notGettingAllUsersListWithoutToken() {

        Response response = BaseTest.getRequestWithoutToken("/api/users", 401);
        String errorMessage = response.jsonPath().getString("message");
        assertEquals("Authentication failed: Full authentication is required to access this resource", errorMessage, "Error message should match");


    }
}




