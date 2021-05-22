package in.reqres.restassured.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Arrays;

import static in.reqres.restassured.config.ConfigHelper.getBaseUri;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CustomSpec {
    /**
     * Формирует для всех запросов базовые параметры URL и фильтры отчета.
     */
    private final RequestSpecification request = given()
            .baseUri(getBaseUri())
            .filters(Arrays.asList(new ResponseLoggingFilter(), new RequestLoggingFilter()))
            .log().uri()
            .when();


    /**
     * Формирует для всех позитивных ответов базовый набор проверок
     */
    private final ResponseSpecification positiveResponse = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    /**
     * Формирует для всех негативных ответов базовый набор проверок
     */
    private final ResponseSpecification negativeResponse = expect()
            .statusCode(400)
            .log().body()
            .body("error", notNullValue());

    public static CustomSpec spec() {
        return new CustomSpec();
    }

    public RequestSpecification request() {
        return request;
    }

    public ResponseSpecification positiveResponse() {
        return positiveResponse;
    }

    public ResponseSpecification negativeResponse() {
        return negativeResponse;
    }
}
