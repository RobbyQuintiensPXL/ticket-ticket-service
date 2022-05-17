package be.jevents.ticketservice.service;


import be.jevents.ticketservice.createresource.CreateFullTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import be.jevents.ticketservice.repository.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TickerServiceTests {

    private final static Long EVENT_ID = 1L;
    @MockBean
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;
    private Ticket ticket;
    private TicketUser ticketUser;

    public void init() {
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

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setEventId(EVENT_ID);
        ticket.setStatus("PAYED");
        ticket.setTicketUser(ticketUser);
    }

    /*@Test
    public void getTicketInfoFromId() {
        init();
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ticket));

        TicketDTO ticketDTO = ticketService.getTicketInfo(ticket.getId());

        assertEquals(ticketDTO.getEventId(), ticket.getEventId());
        assertEquals(ticketDTO.getTicketUserName(), ticket.getTicketUser().getUsername());
    }*/

    @Test
    public void createTicket() {
        init();
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        CreateFullTicketResource ticketResource = new CreateFullTicketResource(
                ticket.getEventId(), ticketUser.getUsername(), ticketUser.getName(),
                ticketUser.getFirstName(), ticketUser.getStreet(), ticketUser.getCity(),
                ticketUser.getZipCode(), ticketUser.getCountry(), ticketUser.getEmail(), ticketUser.getNumberOfTickets()
        );

        ticketService.createTicket(ticketResource, ticketUser.getUsername());
    }
}
