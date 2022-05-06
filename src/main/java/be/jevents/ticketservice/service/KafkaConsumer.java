package be.jevents.ticketservice.service;

import be.jevents.ticketservice.model.Event;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "events", containerFactory = "eventKafkaListenerFactory")
    public void consumeJson(@Payload Event event) {
        System.out.println("Consumed JSON Message: " + event.toString());
    }
}
