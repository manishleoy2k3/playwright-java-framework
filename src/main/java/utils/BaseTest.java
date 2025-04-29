package utils;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import builder.RequestBuilder;

public class BaseTest {

    protected static final String BASE_URL = "https://reqres.in";

    //protected static final int WIREMOCK_PORT = 8089;
    //protected static final String BASE_URI = "http://localhost:" + WIREMOCK_PORT;

    @BeforeClass
    public void globalSetUp() throws Exception {
        WireMockUtils.startServer();
        //RestAssured.baseURI = BASE_URI;
    }

    protected RequestBuilder getBaseRequestBuilder() {
        return new RequestBuilder()
        		.setBaseUri(BASE_URL)
        		.enableLogging();
        
    }
    
    @AfterClass
    public void globalTearDown() {
        WireMockUtils.stopServer();
    }
    
    
}