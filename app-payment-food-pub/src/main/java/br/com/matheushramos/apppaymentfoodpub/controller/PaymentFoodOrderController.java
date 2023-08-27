package br.com.matheushramos.apppaymentfoodpub.controller;

import br.com.matheushramos.apppaymentfoodpub.dto.MessagePublisherResponse;
import br.com.matheushramos.apppaymentfoodpub.dto.PaymentFoodOrder;
import br.com.matheushramos.apppaymentfoodpub.service.PaymentFoodOrderPublisherService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PaymentFoodOrderController {

    @Autowired
    private PaymentFoodOrderPublisherService paymentFoodOrderService;

    @PostMapping("/payment-food-order")
    public ResponseEntity<MessagePublisherResponse> paymentFoodOrder(final @RequestBody PaymentFoodOrder paymentFoodOrder) {
        log.info("PaymentFoodOrderController (/payment-food-order) ::: Received request to payment food. Body: {}",
                new Gson().toJson(paymentFoodOrder));
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentFoodOrderService.processOrder(paymentFoodOrder));
    }

}
