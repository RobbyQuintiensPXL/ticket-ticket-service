package be.jevents.ticketservice.service;

import be.jevents.ticketservice.events.TicketEvent;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
public class KafkaConsumerTests {

    private final static String TOPIC = "testTicket";

    @Autowired
    public KafkaTemplate<String, TicketEvent> template;

    @Autowired
    private KafkaProducer producer;

    private TicketEvent ticketEvent;

    public void init() {
        TicketUser ticketUser = new TicketUser();
        ticketUser.setCountry("Belgium");
        ticketUser.setFirstName("FirstName");

        Event event = new Event();
        event.setEventName("TestEvent");

        Ticket ticket = new Ticket();
        ticket.setUsername("User");

        ticketEvent = new TicketEvent(ticket, event, ticketUser);
    }

//
//    @Test
//    public void givenEmbeddedKafkaBroker_whenSendingtoDefaultTemplate_thenMessageReceived() throws Exception {
//        producer.send(TOPIC, ticketEvent);
//    }

}
