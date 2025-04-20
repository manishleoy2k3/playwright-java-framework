package builder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import java.util.Map;

public class RequestBuilder {
    private RequestSpecification spec;

    public RequestBuilder() {
        spec = given();
    }

    public RequestBuilder setBaseUri(String uri) {
        spec.baseUri(uri);
        return this;
    }

    public RequestBuilder setHeaders(Map<String, String> headers) {
        spec.headers(headers);
        return this;
    }

    public RequestBuilder setBody(Object body) {
        spec.body(body);
        return this;
    }

    public RequestSpecification build() {
        return spec;
    }
}