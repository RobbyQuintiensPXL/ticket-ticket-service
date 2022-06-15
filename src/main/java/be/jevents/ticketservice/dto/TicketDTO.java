package be.jevents.ticketservice.dto;

import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;

public class TicketDTO {

    private final Long id;
    private final Long eventId;
    private final Event event;
    private final String ticketUserName;
    private final int ticketNumber;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.eventId = ticket.getEventId();
        this.event = ticket.getEvent();
        this.ticketUserName = ticket.getUsername();
        this.ticketNumber = ticket.getTicketNumber();
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Event getEvent() {
        return event;
    }

    public String getTicketUserName() {
        return ticketUserName;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

}
