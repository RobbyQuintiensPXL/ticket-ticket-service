package be.jevents.ticketservice.service;

import be.jevents.ticketservice.events.TicketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Configuration
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, TicketEvent> kafkaTemplate;

    public void send(String topic, TicketEvent ticket) {
        LOGGER.info("sending ticket='{}' to topic='{}'", ticket, topic);
        ListenableFuture<SendResult<String, TicketEvent>> future =
                kafkaTemplate.send(topic, ticket);

        future.addCallback(new ListenableFutureCallback<SendResult<String, TicketEvent>>() {
            @Override
            public void onSuccess(SendResult<String, TicketEvent> result) {
                LOGGER.info("Sent ticket=[" + ticket +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Unable to send ticket=["
                        + ticket + "] due to : " + ex.getMessage());
            }
        });
    }
}
