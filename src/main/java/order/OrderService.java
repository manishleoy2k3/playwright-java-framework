package order;


import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class OrderService {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl;

    public OrderService(RestTemplate restTemplate, String paymentServiceUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceUrl = paymentServiceUrl;
    }

    public String createOrder(String orderId, double amount) {
        // Call PaymentService to authorize payment
        String url = paymentServiceUrl + "/payment/authorize?orderId=" + orderId + "&amount=" + amount;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if ("AUTHORIZED".equalsIgnoreCase(response.getBody())) {
            return "ORDER_CONFIRMED";
        } else {
            return "ORDER_FAILED";
        }
    }
}
