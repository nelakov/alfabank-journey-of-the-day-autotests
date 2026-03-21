package utils;

import data.Card;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.net.URI;

import static io.restassured.RestAssured.given;


public class ApiClient {
    private static final URI uri = URI.create(System.getProperty("sut.url"));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(uri.getScheme() + "://" + uri.getHost())
            .setPort(uri.getPort())
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static int getRequestStatusCode(Card card, String path) {
        return given()
                .spec(requestSpec)
                .body(card)
                .when()
                .post(path)
                .getStatusCode();
    }

}
