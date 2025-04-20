package utils;

import builder.RequestBuilder;

public class BaseTest {

    protected static final String BASE_URL = "https://reqres.in";

    protected RequestBuilder getBaseRequestBuilder() {
        return new RequestBuilder().setBaseUri(BASE_URL);
    }
}