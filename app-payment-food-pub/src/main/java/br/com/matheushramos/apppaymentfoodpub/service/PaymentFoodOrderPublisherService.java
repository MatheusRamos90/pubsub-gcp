package br.com.matheushramos.apppaymentfoodpub.service;

import br.com.matheushramos.apppaymentfoodpub.dto.MessagePublisherResponse;
import br.com.matheushramos.apppaymentfoodpub.dto.PaymentFoodOrder;
import br.com.matheushramos.apppaymentfoodpub.exception.PublisherException;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class PaymentFoodOrderPublisherService {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    public MessagePublisherResponse processOrder(final PaymentFoodOrder paymentFoodOrder) {
        CompletableFuture<String> future = this.pubSubTemplate.publish("payment-food",
                PubsubMessage.newBuilder()
                        .setData(ByteString.copyFromUtf8(new Gson().toJson(paymentFoodOrder)))
                        .build())
                .toCompletableFuture();

        try {
            String messageId = future.get(3, TimeUnit.SECONDS);
            return MessagePublisherResponse.builder().messageId(messageId).build();

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new PublisherException("PaymentFoodOrderServiceImpl ::: Error at send message to topic.", e, HttpStatus.BAD_GATEWAY);
        }
    }

}
