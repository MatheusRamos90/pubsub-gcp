package br.com.matheushramos.apppaymentfoodsub.config;

import br.com.matheushramos.apppaymentfoodsub.service.PaymentFoodOrderSubscriberService;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
@Configuration
public class PubSubConfig {

    @Autowired
    private PaymentFoodOrderSubscriberService subscriberService;

    @Value("${spring.cloud.gcp.pubsub.subscriber.name}")
    private String subscriptionName;

    @Bean
    public MessageChannel inputMessageChannel() {
        var pubSubChannel = new PublishSubscribeChannel();
        pubSubChannel.setErrorHandler(t -> {
            t.printStackTrace();
            log.error("There was an error in Pub/Sub channel: {}", t.getMessage());
        });
        return pubSubChannel;
    }

    @Bean
    public PubSubInboundChannelAdapter inboundChannelAdapter(
            @Qualifier("inputMessageChannel") MessageChannel messageChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
        adapter.setOutputChannel(messageChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(
            String payload,
            @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        log.info("Message arrived via an inbound channel adapter from {}! Payload: {}", subscriptionName, payload);
        subscriberService.processMessage(payload);
        message.ack();
    }
}
