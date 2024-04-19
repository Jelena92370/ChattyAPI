package test;

import com.github.javafaker.Faker;
import dto.LoginRequest;
import dto.RegisterRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationTest {
    Faker faker = new Faker();

    @Test
    public void successfulRegistration() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "user";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);

    Response registerResponse = BaseTest.postRequestWithoutToken("/api/auth/register", 200, registerRequest);

        String token = registerResponse.path("accessToken");

        assertFalse(token.isEmpty(), "The access token can not be empty");
        String refreshToken = registerResponse.path("refreshToken");

        assertFalse(refreshToken.isEmpty(), "The refresh token can not be empty");

        int expiration = registerResponse.path("expiration");
        assertTrue(expiration > 0, "The expiration should be greater than 0");

}
    @Test
    public void noRegistrationWithEmailWithoutAt() {
        String email = "newuser.gmail.com";
        String password = faker.internet().password(8, 20);
        String role = "user";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);

        Response registerResponse = BaseTest.postRequestWithoutToken("/api/auth/register", 400, registerRequest);
        String expectedMessage = "Email is not valid.";

        assertEquals(expectedMessage, registerResponse.path("email[0]"));;
}

    @Test
    public void noRegistrationIfPasswordHasLessThanEightSymbols() {
        String email = faker.internet().emailAddress();
        String password = "1234jo";
        String role = "user";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);

        Response registerResponse = BaseTest.postRequestWithoutToken("/api/auth/register", 400, registerRequest);
        String expectedMessage = "Password must contain at least 8 characters";

        assertEquals(expectedMessage, registerResponse.path("password[0]"));
    }
}
