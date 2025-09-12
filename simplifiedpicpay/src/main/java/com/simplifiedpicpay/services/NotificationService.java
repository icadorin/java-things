package com.simplifiedpicpay.services;

import com.simplifiedpicpay.domain.user.User;
import com.simplifiedpicpay.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        try {
            ResponseEntity<String> notificationResponse = restTemplate.postForEntity(
                    "https://util.devi.tools/api/v1/notify",
                    notificationRequest,
                    String.class
            );

            if (!(notificationResponse.getStatusCode().equals(HttpStatus.OK))) {
                System.out.println("Erro ao enviar notificação: " + notificationResponse.getStatusCode());
                throw new RestClientException("HTTP status " + notificationResponse.getStatusCode());
            }
        } catch (RestClientException e) {
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
            throw e;
        }
    }
}
