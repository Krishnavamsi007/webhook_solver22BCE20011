package com.example.webhooksolver.runner;

import com.example.webhooksolver.dto.GenerateWebhookRequest;
import com.example.webhooksolver.dto.GenerateWebhookResponse;
import com.example.webhooksolver.dto.SubmitQueryRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class HiringChallengeRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;

    public HiringChallengeRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String api = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        GenerateWebhookRequest requestBody = new GenerateWebhookRequest("K.Krishnavamsi", "22BCE20011", "krishnavamsikoti@gmail.com");

        ResponseEntity<GenerateWebhookResponse> response = restTemplate.postForEntity(api, requestBody, GenerateWebhookResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String webhook = response.getBody().getWebhook();
            String token = response.getBody().getAccessToken();

            String finalQuery = "SELECT p.amount AS SALARY, CONCAT(e.first_name, ' ', e.last_name) AS NAME, " +
                    "TIMESTAMPDIFF(YEAR, e.dob, CURDATE()) AS AGE, d.department_name AS DEPARTMENT_NAME " +
                    "FROM payments p " +
                    "JOIN employee e ON p.emp_id = e.emp_id " +
                    "JOIN department d ON e.department = d.department_id " +
                    "WHERE DAY(p.payment_time) != 1 " +
                    "ORDER BY p.amount DESC LIMIT 1;";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            SubmitQueryRequest submitRequest = new SubmitQueryRequest(finalQuery);

            HttpEntity<SubmitQueryRequest> entity = new HttpEntity<>(submitRequest, headers);

            restTemplate.exchange(webhook, HttpMethod.POST, entity, String.class);

            System.out.println("✅ Final query submitted successfully!");
        } else {
            System.out.println("❌ Failed to get webhook");
        }
    }
}
