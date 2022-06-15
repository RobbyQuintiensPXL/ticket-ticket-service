package be.jevents.ticketservice.service;


import be.jevents.ticketservice.createresource.CreateFullTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.events.TicketEvent;
import be.jevents.ticketservice.exception.TicketException;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Location;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import be.jevents.ticketservice.repository.TicketRepository;
import be.jevents.ticketservice.service.client.EventFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TickerServiceTests {

    private final static Long EVENT_ID = 1L;
    @MockBean
    private EventFeignClient eventFeignClient;
    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    private Ticket ticket;
    private TicketUser ticketUser;
    private Event event;
    private TicketEvent ticketEvent;

    public void init() {

        ticketUser = new TicketUser();
        ticketUser.setId(1L);
        ticketUser.setName("Name");
        ticketUser.setFirstName("FirstName");
        ticketUser.setEmail("username@test.be");
        ticketUser.setStreet("teststraat 5");
        ticketUser.setZipCode(3500);
        ticketUser.setCity("Hasselt");
        ticketUser.setCountry("Belguium");

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus("PAYED");
        ticket.setTicketUser(ticketUser);
        ticket.setUsername("Username");

        event = new Event();
        event.setId(1L);
        event.setEventName("EventName");
        event.setEventType("Type");
        ticket.setEventId(event.getId());
        ticket.setTicketNumber(1);

        Location location = new Location();
        location.setBuildingName("Building");
        location.setCity("City");

        event.setLocation(location);
        ticket.setEvent(event);

        ticketEvent = new TicketEvent(1, 2, event, ticketUser);
    }

    @Test
    public void getEventInfoTest() {
        init();
        when(eventFeignClient.getEvent(event.getId())).thenReturn(event);

        Event foundEvent = ticketService.getEventInfo(event.getId());

        assertEquals(event.getEventName(), foundEvent.getEventName());
        assertEquals(event.getPrice(), foundEvent.getPrice());
    }

    @Test
    public void getTicketInfoFromId() {
        init();
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ticket));
        when(eventFeignClient.getEvent(event.getId())).thenReturn(event);

        TicketDTO ticketDTO = ticketService.getTicketInfo(ticket.getId());

        assertEquals(ticketDTO.getEventId(), ticket.getEventId());
        assertEquals(ticketDTO.getEvent().getEventName(), ticket.getEvent().getEventName());
        assertEquals(ticketDTO.getEvent().getEventType(), ticket.getEvent().getEventType());
    }

//    @Test
//    public void createTicket() {
//        init();
//        ticketService = mock(TicketService.class);
//        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
//        when(eventFeignClient.getEvent(event.getId())).thenReturn(event);
//        when(ticketService.createTicketEvent(2, 1, any(Event.class), any(TicketUser.class))).thenReturn(ticketEvent);
//
//        CreateFullTicketResource ticketResource = new CreateFullTicketResource(
//                ticket.getEventId(), ticket.getUsername(), ticketUser.getName(),
//                ticketUser.getFirstName(), ticketUser.getStreet(), ticketUser.getCity(),
//                ticketUser.getZipCode(), ticketUser.getCountry(), ticketUser.getEmail(),
//        );
//
//        ticketService.createTicket(ticketResource, ticket.getUsername());
//
//        assertEquals(ticketResource.getEventId(), ticket.getEventId());
//    }

    @Test
    public void getEventsByUserTest() {
        init();
        List<Ticket> ticketList = new LinkedList<>();
        ticketList.add(ticket);
        when(ticketRepository.findTicketByUsername(anyString())).thenReturn(ticketList);

        List<TicketDTO> ticketDTOList = ticketService.getEventsByUser(ticket.getUsername());

        assertEquals(ticketList.size(), ticketDTOList.size());
        assertEquals(ticketList.get(0).getEventId(), ticketDTOList.get(0).getEventId());
    }

    @Test
    public void getSoldTicketsAmountForEventTest(){
        init();
        List<Ticket> ticketList = new LinkedList<>();
        ticketList.add(ticket);
        when(ticketRepository.findTicketsByEventId(anyLong())).thenReturn(ticketList);

        int amount = ticketService.getSoldTicketsAmountForEvent(event.getId());

        assertEquals(amount, ticketList.size());
    }

    @Test
    public void validateTicketTest(){
        init();
        when(ticketRepository.findTicketByTicketNumberAndEventIdAndTicketUser_Id(anyInt(), anyLong(), anyLong()))
                .thenReturn(Optional.ofNullable(ticket));

        ticketService.validateTicket(ticket.getTicketNumber(), ticket.getEventId(), ticket.getTicketUser().getId());

        assertTrue(ticket.isValidated());
    }

    @Test(expected = TicketException.class)
    public void throwExceptionTicketByIdNotFound() {
        ticketService.getTicketInfo(anyLong());
    }

    @Test(expected = TicketException.class)
    public void throwExceptionTEventByUserNotFound() {
        ticketService.getEventsByUser(anyString());
    }

    @Test(expected = TicketException.class)
    public void throwExceptionTicketAlreadyValidated() {
        ticketService.validateTicket(anyInt(), anyLong(), anyLong());
    }


}
