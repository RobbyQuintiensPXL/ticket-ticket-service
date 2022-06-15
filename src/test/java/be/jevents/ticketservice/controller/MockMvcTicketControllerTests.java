package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.service.TicketService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class MockMvcTicketControllerTests {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;

    private Ticket ticket;

    public void init(){
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setEventId(1L);
        ticket.setStatus("PAYED");
        ticket.setUsername("Username");
    }

    @Test
    public void getTicketInfoTest() throws Exception {
        init();
        when(ticketService.getTicketInfo(anyLong()))
                .thenReturn(new TicketDTO(ticket));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/tickets/ticket/{ticketId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ticket.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketUserName").value(ticket.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventId").value(ticket.getEventId()));
    }

    @Test
    public void getAmountOfTicketsLeftTest() throws Exception {
        init();
        when(ticketService.getSoldTicketsAmountForEvent(anyLong()))
                .thenReturn(2);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/tickets/ticket/{eventId}/ticketsleft", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(2));
    }


}
