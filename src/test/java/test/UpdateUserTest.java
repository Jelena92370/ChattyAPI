package test;

import dto.UpdateProfileRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserTest {

    @Test
    public void successfulNameUpdate() {

        String token = Helper.userRegistration();

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);
        String id = userInfoResponse.jsonPath().getString("id");

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        updateProfileRequest.setName("UpdatedName");
        String endPoint = "/api/users/" + id;

        Response response = BaseTest.putRequest(token, endPoint, 200, updateProfileRequest);
        String updatedName = response.jsonPath().getString("name");
        assertEquals("UpdatedName", updatedName);
        String receivedId = response.jsonPath().getString("id");
        assertEquals(id, receivedId);


}

    @Test
    public void noNameUpdateIfItContainsNumbers() {

        String token = Helper.userRegistration();

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);
        String id = userInfoResponse.jsonPath().getString("id");

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        updateProfileRequest.setName("Name12343");
        String endPoint = "/api/users/" + id;

        Response response = BaseTest.putRequest(token, endPoint, 400, updateProfileRequest);


        String errorMessage = response.jsonPath().getString("name[0]");


        assertTrue(errorMessage.contains("must match \"^[A-Za-z-]+$\""));
}

    @Test
    public void noNameUpdateWithNotValidIdType() {

        String token = Helper.userRegistration();

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);
        String id = "ID";

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        updateProfileRequest.setName("UpdatedName");
        String endPoint = "/api/users/" + id;

        Response response = BaseTest.putRequest(token, endPoint, 400, updateProfileRequest);


        String errorMessage = response.jsonPath().getString("message");


        assertTrue(errorMessage.contains("Invalid UUID string: ID"));
    }

    @Test
    public void noNameUpdateWithEmptyId() {

        String token = Helper.userRegistration();

        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);
        String id = "";

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();

        updateProfileRequest.setName("UpdatedName");
        String endPoint = "/api/users/" + id;

        Response response = BaseTest.putRequest(token, endPoint, 400, updateProfileRequest);


        String errorMessage = response.jsonPath().getString("httpStatus");


        assertTrue(errorMessage.contains("Invalid UUID string"));
    }
}
