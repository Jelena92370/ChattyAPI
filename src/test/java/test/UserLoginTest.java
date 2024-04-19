package test;

import dto.LoginRequest;
import dto.UserResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLoginTest {

    @Test
    public void successfulLogin() {

        LoginRequest loginRequest = new LoginRequest("pince5@gmail.com", "Lena13456");

       Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 200, loginRequest);

        String token = loginResponse.path("accessToken");

        assertFalse(token.isEmpty(), "The access token can not be empty");
        String refreshToken = loginResponse.path("refreshToken");

        assertFalse(refreshToken.isEmpty(), "The refresh token can not be empty");

        int expiration = loginResponse.path("expiration");
        assertTrue(expiration > 0, "The expiration should be greater than 0");

    }
    @Test
    public void noLoginWithInvalidEmail() {

        LoginRequest loginRequest = new LoginRequest("pince5@gm.com", "Lena13456");

        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 401, loginRequest);
        String expectedMessage = "User not found!";

        assertEquals(expectedMessage, loginResponse.path("message"), "The error message should match.");
    }


    @Test
    public void noLoginWithEmptyEmail() {

        LoginRequest loginRequest = new LoginRequest("", "Lena13456");

        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 400, loginRequest);
        List<String> errorMessages = loginResponse.path("email");
        assertTrue(errorMessages.contains("Email cannot be empty"), "Email cannot be empty message is missing");
        assertTrue(errorMessages.contains("Invalid email format"), "Invalid email format message is missing");
    }

    @Test
    public void noLoginWithEmptyPassword() {

        LoginRequest loginRequest = new LoginRequest("pince5@gmail.com", "");

        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 400, loginRequest);
        List<String> errorMessages = loginResponse.path("password");
        assertTrue(errorMessages.contains("Password cannot be empty"), "Password message is missing");
        assertTrue(errorMessages.contains("Password must contain at least 8 characters"), "Invalid password format message is missing");
    }


}
