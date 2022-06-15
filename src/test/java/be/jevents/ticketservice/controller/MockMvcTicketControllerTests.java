package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.service.TicketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTicketControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

//    @Test
//    public void getTicketInfoTest(){
//        TicketDTO ticket = mock(TicketDTO.class);
//        when(ticketService.getTicketInfo(anyLong())).thenReturn(ticket);
//        //mockMvc.perform(get("/ticket/{ticketId}"))
//    }
}
