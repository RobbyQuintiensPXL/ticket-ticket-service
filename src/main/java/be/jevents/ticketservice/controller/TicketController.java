package be.jevents.ticketservice.controller;

import be.jevents.ticketservice.createresource.CreateFullTicketResource;
import be.jevents.ticketservice.dto.TicketDTO;
import be.jevents.ticketservice.filter.UserNameFilter;
import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.service.TicketService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventInfo(@PathVariable("eventId") Long id) {
        return new ResponseEntity<>(ticketService.getEventInfo(id), HttpStatus.OK);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketInfo(@PathVariable("ticketId") Long id) {
        return new ResponseEntity<>(ticketService.getTicketInfo(id), HttpStatus.OK);
    }

    @GetMapping("/ticket/{eventId}/ticketsleft")
    public ResponseEntity<Integer> getAmountOfTicketsLeft(@PathVariable("eventId") Long id) {
        return new ResponseEntity<>(ticketService.getSoldTicketsAmountForEvent(id), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@RequestHeader HttpHeaders token) {
        ticketService.getEventsByUser(getUser(token));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{eventId}/order")
    public ResponseEntity<Void> createTicket(@RequestHeader HttpHeaders token,
                                             @RequestBody @Valid CreateFullTicketResource ticketResource) throws IOException, FileUploadException {
        ticketService.createTicket(ticketResource, getUser(token));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/validate/{eventId}/{ticketNumber}/{ticketUser}")
    public ResponseEntity<Void> validateTicket(@PathVariable("ticketNumber") int ticketNumber,
                                               @PathVariable("eventId") Long eventId,
                                               @PathVariable("ticketUser") Long ticketUser) {
        ticketService.validateTicket(ticketNumber, eventId, ticketUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private String getUser(HttpHeaders token) {
        UserNameFilter filter = new UserNameFilter();
        return filter.getUsername(token);
    }

}
