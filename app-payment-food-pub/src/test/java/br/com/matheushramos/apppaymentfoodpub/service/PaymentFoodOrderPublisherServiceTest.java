package br.com.matheushramos.apppaymentfoodpub.service;

import br.com.matheushramos.apppaymentfoodpub.dto.FoodData;
import br.com.matheushramos.apppaymentfoodpub.dto.MessagePublisherResponse;
import br.com.matheushramos.apppaymentfoodpub.dto.PaymentFoodOrder;
import br.com.matheushramos.apppaymentfoodpub.exception.PublisherException;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.PubsubMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentFoodOrderPublisherServiceTest {

    @InjectMocks
    private PaymentFoodOrderPublisherService service;

    @Mock
    private PubSubTemplate pubSubTemplate;

    @Test
    void testProcessOrder_successfulyInPublishMessage() {
        PaymentFoodOrder order = createOrder();

        CompletableFuture<String> future = CompletableFuture.completedFuture("1");

        when(pubSubTemplate.publish(eq("payment-food"), any(PubsubMessage.class))).thenReturn(future);

        MessagePublisherResponse response = service.processOrder(order);

        assertEquals("1", response.getMessageId());

        verify(pubSubTemplate, times(1)).publish(eq("payment-food"), any(PubsubMessage.class));
    }

    @Test
    void testProcessOrder_errorInPublishMessage() {
        PaymentFoodOrder order = createOrder();

        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new TimeoutException("Error"));

        when(pubSubTemplate.publish(eq("payment-food"), any(PubsubMessage.class))).thenReturn(future);

        PublisherException ex = assertThrows(PublisherException.class, () -> service.processOrder(order));

        assertEquals("PaymentFoodOrderServiceImpl ::: Error at send message to topic.", ex.getMessage());

        verify(pubSubTemplate).publish(eq("payment-food"), any(PubsubMessage.class));
    }

    private PaymentFoodOrder createOrder() {
        return PaymentFoodOrder.builder()
                .orderId(UUID.randomUUID())
                .foodData(List.of(FoodData.builder()
                        .name("Food01")
                        .refId("#055")
                        .price(BigDecimal.valueOf(20L))
                        .build()))
                .build();
    }

}