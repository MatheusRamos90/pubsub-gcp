package br.com.matheushramos.apppaymentfoodpub.config;

import com.google.cloud.spring.core.GcpProjectIdProvider;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class PubSubConfig {

    @Value("${spring.cloud.gcp.pubsub.topic}")
    private String topicName;

    @Autowired
    private GcpProjectIdProvider projectIdProvider;

    @Bean
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, this.topicName);

        adapter.setSuccessCallback(
                ((ackId, message) ->
                        log.info("Message was sent via the outbound channel adapter to {}!", this.topicName)));

        adapter.setFailureCallback(
                (cause, message) -> log.info("Error sending " + message + " due to " + cause));

        return adapter;
    }
}
