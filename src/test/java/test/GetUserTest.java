package test;

import dto.LoginRequest;
import dto.UserResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetUserTest {

    @Test
    public void getRequestUserInformationAfterLogin() {
       String email = "pince5@gmail.com";
       String password = "Lena13456";

       String token = Helper.userLogin(email, password);

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);


        UserResponse userResponse = userInfoResponse.as(UserResponse.class);

        assertNotNull(userResponse.getId(), "User ID should not be null");

    }

    @Test
    public void getRequestUserInformationAfterRegistration() {

        String token = Helper.userRegistration();

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);


        UserResponse userResponse = userInfoResponse.as(UserResponse.class);

        assertNotNull(userResponse.getId(), "User ID should not be null");


    }

    @Test
    public void getRequestUserInformationWithEmptyToken() {

        String token = "";

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 401);

        String expectedMessage = "Authentication failed: Full authentication is required to access this resource";

        assertEquals(expectedMessage, userInfoResponse.path("message"), "Error message should match.");

        String expectedHttpStatus = "UNAUTHORIZED";
        assertEquals(expectedHttpStatus, userInfoResponse.path("httpStatus"), "Status should be UNAUTHORIZED.");
    }
}
