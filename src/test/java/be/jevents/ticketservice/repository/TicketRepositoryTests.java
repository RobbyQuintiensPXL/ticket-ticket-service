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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class TicketRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket ticket;
    private TicketUser ticketUser;

    public void persist() {
        ticketUser = new TicketUser();
        ticketUser.setName("Name");
        ticketUser.setFirstName("FirstName");
        ticketUser.setEmail("username@test.be");
        ticketUser.setStreet("teststraat 5");
        ticketUser.setZipCode(3500);
        ticketUser.setCity("Hasselt");
        ticketUser.setCountry("Belguium");
        // ticketUser.setNumberOfTickets(1);

        entityManager.persist(ticketUser);

        ticket = new Ticket();
        ticket.setEventId(1L);
        ticket.setStatus("PAYED");
        ticket.setTicketUser(ticketUser);
        ticket.setUsername("Username");

        entityManager.persist(ticket);
        entityManager.flush();
    }

    @Test
    public void findAllTickets() {
        persist();
        List<Ticket> ticketList = ticketRepository.findAll();

        assertThat(ticketList).isNotEmpty();
        assertThat(ticketList.get(0).getStatus()).isEqualTo(ticket.getStatus());
        assertThat(ticketList.get(0).getEventId()).isEqualTo(ticket.getEventId());
        assertThat(ticketList.get(0).getTicketUser().getEmail()).isEqualTo(ticketUser.getEmail());
    }

    @Test
    public void findTicketsByEventIdTest() {
        persist();
        List<Ticket> ticketList = ticketRepository.findTicketsByEventId(1L);

        assertThat(ticketList).isNotEmpty();
        assertThat(ticketList.get(0).getStatus()).isEqualTo(ticket.getStatus());
        assertThat(ticketList.get(0).getEventId()).isEqualTo(ticket.getEventId());
        assertThat(ticketList.get(0).getTicketUser().getEmail()).isEqualTo(ticket.getTicketUser().getEmail());
    }

    @Test
    public void findTicketByUsernameTest() {
        persist();
        List<Ticket> ticketList = ticketRepository.findTicketByUsername(ticket.getUsername());

        assertThat(ticketList).isNotEmpty();
        assertThat(ticketList.get(0).getStatus()).isEqualTo(ticket.getStatus());
        assertThat(ticketList.get(0).getEventId()).isEqualTo(ticket.getEventId());
        assertThat(ticketList.get(0).getTicketUser().getEmail()).isEqualTo(ticket.getTicketUser().getEmail());
    }

    @Test
    public void findTicketByTicketNumberAndEventIdAndTicketUser_IdTest() {
        persist();
        Optional<Ticket> foundTicket =
                ticketRepository.findTicketByTicketNumberAndEventIdAndTicketUser_Id(ticket.getTicketNumber(),
                        ticket.getEventId(), ticketUser.getId());

        assertThat(foundTicket).isNotEmpty();
        assertThat(foundTicket.get().getStatus()).isEqualTo(ticket.getStatus());
        assertThat(foundTicket.get().getUsername()).isEqualTo(ticket.getUsername());
        assertThat(foundTicket.get().getTicketUser().getEmail()).isEqualTo(ticket.getTicketUser().getEmail());
    }
}
