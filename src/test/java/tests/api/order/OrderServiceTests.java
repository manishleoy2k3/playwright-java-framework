package tests.api.order;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

import order.OrderService;
import utils.BaseTest;
import utils.WireMockUtils;

import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.*;


public class OrderServiceTests extends BaseTest{

    private OrderService orderService;

    @BeforeMethod
    void setup() {
    	RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + WireMockUtils.getWireMockServerPort();
        System.out.println("Base URL = " + baseUrl);
        orderService = new OrderService(restTemplate, baseUrl);
    }

    @Test
    void testCreateOrder_Authorized() {
        // Arrange: Stubbing PaymentService response
        stubFor(get(urlPathEqualTo("/payment/authorize"))
                .withQueryParam("orderId", equalTo("123"))
                .withQueryParam("amount", equalTo("1000.0"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("AUTHORIZED")));

        // Act
        String result = orderService.createOrder("123", 1000.0);

        // Assert
        Assert.assertEquals("ORDER_CONFIRMED", result);
    }

    @Test
    void testCreateOrder_Unauthorized() {
        // Arrange: Stubbing PaymentService to return Unauthorized
        stubFor(get(urlPathEqualTo("/payment/authorize"))
                .withQueryParam("orderId", equalTo("124"))
                .withQueryParam("amount", equalTo("500.0"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("DECLINED")));

        // Act
        String result = orderService.createOrder("124", 500.0);

        Assert.assertEquals("ORDER_FAILED", result);
    }
}
