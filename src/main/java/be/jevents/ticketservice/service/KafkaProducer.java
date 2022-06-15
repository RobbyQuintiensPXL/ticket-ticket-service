package be.jevents.ticketservice.service;

import be.jevents.ticketservice.events.TicketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC = "ticket";

    @Autowired
    private KafkaTemplate<String, TicketEvent> kafkaTemplate;

    public void send(TicketEvent ticket) {
        LOGGER.info("sending ticket='{}", ticket.getTicketId());
        Message<TicketEvent> message = MessageBuilder
                .withPayload(ticket)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();
        this.kafkaTemplate.send(message);
    }
}
