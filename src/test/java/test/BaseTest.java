package test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final static String BASE_URI = "http://chatty.telran-edu.de:8989";

    static RequestSpecification specWithoutAppID = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .build();

    static RequestSpecification specificationWithToken (String accessToken) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeader("Authorization", "Bearer " + accessToken)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static Response postRequestWithoutToken(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specWithoutAppID)
                .body(body)
                .when().log().all()
                .post(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequestWithToken(String accessToken, String endPoint, Integer expectedStatusCode, Object body) {
        RequestSpecification specWithToken = specificationWithToken(accessToken);

              Response response = given()
                .spec(specWithToken)
                .body(body)
                .when().log().all()
                .post(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();

        return response;
    }

    public static Response getRequest(String accessToken, String endPoint, Integer expectedStatusCode) {
        RequestSpecification specWithToken = specificationWithToken(accessToken);
        Response response = given()
                .spec(specWithToken)
                .when().log().all()
                .get(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }

    public static Response getRequestWithoutToken(String endPoint, Integer expectedStatusCode) {

        Response response = given()
                .spec(specWithoutAppID)
                .when().log().all()
                .get(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }


    public static Response putRequest(String accessToken, String endPoint, Integer expectedStatusCode, Object body) {

        RequestSpecification specWithToken = specificationWithToken(accessToken);
        Response response = given()
                .spec(specWithToken)
                .body(body)
                .when().log().all()
                .put(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response deleteRequest(String accessToken, String endPoint, Integer expectedStatusCode) {
        RequestSpecification specWithToken = specificationWithToken(accessToken);
        Response response = given()
                .spec(specWithToken)
                .when().log().all()
                .delete(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
}
