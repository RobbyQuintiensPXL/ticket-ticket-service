package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.createresource.CreateTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventInfo(@PathVariable("eventId") Long id){
        return new ResponseEntity<>(ticketService.getEventInfo(id), HttpStatus.OK);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketInfo(@PathVariable("ticketId") Long id){
        return new ResponseEntity<>(ticketService.getTicketInfo(id), HttpStatus.OK);
    }

    @GetMapping("/ticket/{eventId}/ticketsleft")
    public ResponseEntity<Integer> getAmountOfTicketsLeft(@PathVariable("eventId") Long id){
        return new ResponseEntity<>(ticketService.getSoldTicketsAmountForEvent(id), HttpStatus.OK);
    }

}
