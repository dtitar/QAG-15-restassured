package in.reqres.restassured.tests;


import in.reqres.restassured.api.model.AccountData;
import in.reqres.restassured.api.model.Resource;
import in.reqres.restassured.testdata.User;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static in.reqres.restassured.api.spec.CustomSpec.spec;
import static in.reqres.restassured.config.ConfigHelper.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("api tests for https://reqres.in/")
public class ApiTests {


    @Test
    void testResourcesCommonData() {

        final int expectedPage = 1;
        final int expectedPerPage = 6;
        final int expectedTotal = 12;
        final int expectedTotalPages = 2;

        spec().request()
                .get(getResourceEndpoint())
                .then()
                .spec(spec().positiveResponse())
                .body("page", equalTo(expectedPage))
                .and()
                .body("per_page", equalTo(expectedPerPage))
                .and()
                .body("total", equalTo(expectedTotal))
                .and()
                .body("total_pages", equalTo(expectedTotalPages));
    }

    @Test
    void testEachResourceHasNotNullId() {

        Resource[] resources =
                spec().request()
                        .get(getResourceEndpoint())
                        .then()
                        .spec(spec().positiveResponse())
                        .extract()
                        .jsonPath()
                        .getObject("data", Resource[].class);

        for (Resource resource : resources) {
            assertThat(resource.getId()).isNotNull();
        }
    }

    @Test
    void testSuccessfulRegister() {
        User user = new User("eve.holt@reqres.in", "pistol");
        String expectedId = "4";
        String expectedToken = "QpwL5tke4Pnpja7X4";

        AccountData accountData =
                given()
                        .baseUri(getBaseUri())
                        .contentType(ContentType.JSON)
                        .filters(Arrays.asList(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                        .body(user)
                        .post(getRegisterEndpoint())
                        .then()
                        .spec(spec().positiveResponse())
                        .extract()
                        .as(AccountData.class);

        assertThat(accountData.getId()).isEqualTo(expectedId);
        assertThat(accountData.getToken()).isEqualTo(expectedToken);
    }

    @Test
    void testUnsuccessfulRegister() {
        String email = "sydney@fife";
        String expectedError = "Missing password";
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        given()
                .baseUri(getBaseUri())
                .contentType(ContentType.JSON)
                .filters(Arrays.asList(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                .body(body)
                .post(getRegisterEndpoint())
                .then()
                .spec(spec().negativeResponse())
                .body("error", equalTo(expectedError));
    }

    @Test
    void testUserNotFound() {
        spec().request()
                .get(getUsersEndpoint() + "/23")
                .then()
                .statusCode(404);
    }
}
