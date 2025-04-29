package tests.api.order;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import order.OrderService;

public class OrderServiceMockitoTests {

    @Mock
    private RestTemplate restTemplate;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String paymentServiceBaseUrl = "http://mock-payment-service";
        orderService = new OrderService(restTemplate, paymentServiceBaseUrl);
    }

    @Test
    void testCreateOrder_Authorized() {
        // Arrange
        String expectedUrl = "http://mock-payment-service/payment/authorize?orderId=123&amount=1000.0";
        when(restTemplate.getForEntity(expectedUrl, String.class))
                .thenReturn(ResponseEntity.ok("AUTHORIZED"));

        // Act
        String result = orderService.createOrder("123", 1000.0);

        // Assert
        assertEquals("ORDER_CONFIRMED", result);
    }

    @Test
    void testCreateOrder_Unauthorized() {
        // Arrange
        String expectedUrl = "http://mock-payment-service/payment/authorize?orderId=124&amount=500.0";
        when(restTemplate.getForEntity(expectedUrl, String.class))
                .thenReturn(ResponseEntity.ok("DECLINED"));

        // Act
        String result = orderService.createOrder("124", 500.0);

        // Assert
        assertEquals("ORDER_FAILED", result);
    }
}
