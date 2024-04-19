package test;

import dto.RegisterResponse;
import dto.TokenRefreshRequest;
import dto.TokenRefreshResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenTest {
    @Test
    public void successfulRefreshRequest() throws InterruptedException {


        RegisterResponse registerResponse = Helper.userRegistrationFullResponse();
        String initialAccessToken = registerResponse.getAccessToken();
        String initialRefreshToken = registerResponse.getRefreshToken();


        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest();
        tokenRefreshRequest.setRefreshToken(initialRefreshToken);


        Response refreshApiResponse = BaseTest.postRequestWithToken(initialAccessToken, "/api/auth/refresh", 201, tokenRefreshRequest);
        TokenRefreshResponse updatedTokensResponse = refreshApiResponse.as(TokenRefreshResponse.class);


        assertNotNull(updatedTokensResponse.getAccessToken(), "New access token should not be null");
        assertNotNull(updatedTokensResponse.getRefreshToken(), "New refresh token should not be null");


        assertNotEquals(initialAccessToken, updatedTokensResponse.getAccessToken(), "New access token should be different from the old one");
        assertNotEquals(initialRefreshToken, updatedTokensResponse.getRefreshToken(), "New refresh token should be different from the old one");


    }

    @Test
    public void noRefreshWithInvalidToken() {
        RegisterResponse registerResponse = Helper.userRegistrationFullResponse();
        String initialAccessToken = registerResponse.getAccessToken();
        String invalidRefreshToken = "someInvalidToken12345";

        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest();
        tokenRefreshRequest.setRefreshToken(invalidRefreshToken);


        Response refreshApiResponse = BaseTest.postRequestWithToken(initialAccessToken, "/api/auth/refresh", 401, tokenRefreshRequest);


        String errorMessage = refreshApiResponse.jsonPath().getString("message");

        assertTrue(errorMessage.contains("Unauthorized"), "Error message should be 'Unauthorized'");
    }


}







