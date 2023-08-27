package br.com.matheushramos.apppaymentfoodsub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Matheus Ramos
 * This class was created to demonstrate that we can receive messages from topic by a endpoint after the message to arrive in topic.
 * Called trigger endpoint too.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/payment/food")
public class PaymentOrderFoodNotificationController {

    @PostMapping("/order")
    public ResponseEntity<String> paymentOrderFoodNotification() {
        log.info("Notification received from Pub/Sub to push endpoint after message to arrive in topic");
        return ResponseEntity.ok("Notification received");
    }

}
