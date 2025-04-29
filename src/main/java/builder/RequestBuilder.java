package builder;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class RequestBuilder {

    private RequestSpecification request;

    public RequestBuilder() {
        this.request = given();
    }

    public RequestBuilder setBaseUri(String baseUri) {
        request.baseUri(baseUri);
        return this;
    }

    public RequestBuilder setHeaders(Map<String, String> headers) {
        request.headers(headers);
        return this;
    }

    public RequestBuilder setBody(Object body) {
        request.body(body);
        return this;
    }

    public RequestBuilder setAuthToken(String token) {
        request.header("Authorization", "Bearer " + token);
        return this;
    }

    public RequestBuilder enableLogging() {
        request = request.log().all();
        return this;
    }

    public RequestSpecification build() {
        return request.contentType(ContentType.JSON);
    }
}