package be.jevents.ticketservice.events;

import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketEvent {

    private final Long id;
    private final Long eventId;
    private final String ticketUserName;
    private final String firstName;
    private final String name;
    private final String email;
    private final String eventName;
    private final String eventType;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private final LocalDate eventDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime eventTime;
    private final double price;
    private final String buildingName;

    public TicketEvent(Ticket ticket, Event event, TicketUser ticketUser) {
        this.id = ticket.getId();
        this.eventId = ticket.getEventId();
        this.ticketUserName = ticket.getUsername();
        this.firstName = ticketUser.getFirstName();
        this.name = ticketUser.getName();
        this.email = ticketUser.getEmail();
        this.eventName = event.getEventName();
        this.eventType = event.getEventType();
        this.eventDate = event.getEventDate();
        this.eventTime = event.getEventTime();
        this.price = event.getPrice();
        this.buildingName = event.getLocation().getBuildingName();
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getTicketUserName() {
        return ticketUserName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public double getPrice() {
        return price;
    }

    public String getBuildingName() {
        return buildingName;
    }
}
