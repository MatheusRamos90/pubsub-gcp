package br.com.matheushramos.apppaymentfoodsub.service;

import br.com.matheushramos.apppaymentfoodsub.dto.PaymentFoodOrder;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentFoodOrderSubscriberService {

    public void processMessage(final String message) {
        var messageConverted = new Gson().fromJson(message, PaymentFoodOrder.class);
        log.info("Message converted to object: {}", messageConverted);

        // Here you can do anything with your message. Save the information in a database or call other APIs to pass the informations.
    }

}
