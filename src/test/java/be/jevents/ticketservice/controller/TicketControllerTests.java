package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.controller.controlleradvice.TicketControllerAdvice;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import be.jevents.ticketservice.service.TicketService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@ImportAutoConfiguration(TicketControllerAdvice.class)
public class TicketControllerTests {

    @InjectMocks
    TicketController ticketController;

    @Mock
    TicketService ticketService;

    private Ticket ticket;
    private TicketUser ticketUser;
    private Event event;

    public void init() {
        List<Ticket> ticketList = new LinkedList<>();
        ticket = mock(Ticket.class);
        event = mock(Event.class);
        ticketUser = mock(TicketUser.class);
        ticketList.add(ticket);
    }

    @Test
    public void getTicketInfoTest() {
        init();
        TicketDTO ticketDTO = Optional.of(ticket).stream().map(TicketDTO::new).findAny().get();
        when(ticketService.getTicketInfo(ticket.getId())).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> responseEntity = ticketController.getTicketInfo(ticket.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getEventId(), ticket.getEventId());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getTicketNumber(), ticket.getTicketNumber());
    }

    @Test
    public void getEventInfoTest() {
        init();
        when(ticketService.getEventInfo(event.getId())).thenReturn(event);

        ResponseEntity<Event> responseEntity = ticketController.getEventInfo(event.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getEventName(), event.getEventName());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPrice(), event.getPrice());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), event.getId());
        assertEquals(Objects.requireNonNull(responseEntity.getBody()).getLocation(), event.getLocation());
    }

    @Test
    public void getAmountOfTicketsLeftTest() {
        init();
        int amount = 2;
        when(ticketService.getSoldTicketsAmountForEvent(event.getId())).thenReturn(amount);

        ResponseEntity<Integer> responseEntity = ticketController.getAmountOfTicketsLeft(event.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertEquals(responseEntity.getBody(), amount);
    }

    @Test
    public void validateTicketTest() {
        init();
        ResponseEntity<Void> responseEntity = ticketController.validateTicket(ticket.getTicketNumber(),
                ticket.getEventId(), ticketUser.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

/*    @Test
    public void getTicketsByUserTest(){

    }*/

}
