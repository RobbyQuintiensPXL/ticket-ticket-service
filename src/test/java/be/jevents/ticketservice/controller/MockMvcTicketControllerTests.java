package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.createresource.CreateFullTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import be.jevents.ticketservice.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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
        TicketUser ticketUser = new TicketUser();
        ticketUser.setId(1L);
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setEventId(1L);
        ticket.setTicketNumber(1);
        ticket.setStatus("PAYED");
        ticket.setUsername("Username");
        ticket.setTicketUser(ticketUser);
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

    @Test
    public void getTicketsByUserTest() throws Exception {
        init();
        List<TicketDTO> ticketDTOList = new LinkedList<>();
        ticketDTOList.add(new TicketDTO(ticket));
        when(ticketService.getEventsByUser(anyString()))
                .thenReturn(ticketDTOList);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/tickets/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createTicketTest() throws Exception {
        init();
        List<TicketDTO> ticketDTOList = new LinkedList<>();
        ticketDTOList.add(new TicketDTO(ticket));
        when(ticketService.getEventsByUser(anyString()))
                .thenReturn(ticketDTOList);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/tickets/{eventId}/order", 1)
                        .content(asJsonString(new CreateFullTicketResource(1L, "user",
                                "Lastname", "Firstname", "street", "city",
                                3500, "Country", "email", 1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void validateTicketTest() throws Exception {
        init();
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tickets/validate/{eventId}/{ticketNumber}/{ticketUser}",
                                1, ticket.getTicketNumber(), ticket.getTicketUser().getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
