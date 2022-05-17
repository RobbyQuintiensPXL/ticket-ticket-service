package be.jevents.ticketservice.repository;


import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class TicketUserRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TicketUserRepository ticketUserRepository;

    private Ticket ticket;
    private TicketUser ticketUser;

    public void persist() {
        ticketUser = new TicketUser();
        ticketUser.setName("Name");
        ticketUser.setFirstName("FirstName");
        ticketUser.setUsername("Username");
        ticketUser.setEmail("username@test.be");
        ticketUser.setStreet("teststraat 5");
        ticketUser.setZipCode(3500);
        ticketUser.setCity("Hasselt");
        ticketUser.setCountry("Belguium");
        ticketUser.setNumberOfTickets(1);

        entityManager.persist(ticketUser);

        ticket = new Ticket();
        ticket.setEventId(1L);
        ticket.setStatus("PAYED");
        ticket.setTicketUser(ticketUser);

        entityManager.persist(ticket);
        entityManager.flush();
    }

    @Test
    public void findAllTicketUsers() {
        persist();
        List<TicketUser> ticketUserList = ticketUserRepository.findAll();

        assertThat(ticketUserList).isNotEmpty();
        assertThat(ticketUserList.get(0).getEmail()).isEqualTo(ticketUser.getEmail());
        assertThat(ticketUserList.get(0).getCity()).isEqualTo(ticketUser.getCity());
        assertThat(ticketUserList.get(0).getUsername()).isEqualTo(ticketUser.getUsername());
        assertThat(ticketUserList.get(0).getStreet()).isEqualTo(ticketUser.getStreet());
    }
}
