package be.jevents.ticketservice.service;

import be.jevents.ticketservice.events.TicketEvent;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Location;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mock;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaProducerTests {

    @Autowired
    private KafkaProducer producer;

    @MockBean
    private KafkaConsumer consumer;

    @Value("${test.topic}")
    private String topic;

    private TicketEvent ticketEvent;

    public void init() {
        TicketUser ticketUser = new TicketUser();
        ticketUser.setCountry("Belgium");
        ticketUser.setFirstName("FirstName");

        Event event = new Event();
        event.setEventName("TestEvent");

        Ticket ticket = new Ticket();
        ticket.setUsername("User");

        Location location = new Location();
        location.setCity("City");

        event.setLocation(location);

        ticketEvent = new TicketEvent(1, 2, event, ticketUser);
    }


    @Test
    public void sendTest() {
        init();
        producer = mock(KafkaProducer.class);
        producer.send(ticketEvent);

    }

}
