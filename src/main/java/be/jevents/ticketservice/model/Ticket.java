package be.jevents.ticketservice.model;

import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Transient
    private Event event;

    @ManyToOne
    private TicketUser ticketUser;

    @Column(name = "ticket_number")
    private int ticketNumber;

    private String status;

    public Ticket(){
        //Empty Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TicketUser getTicketUser() {
        return ticketUser;
    }

    public void setTicketUser(TicketUser ticketUser) {
        this.ticketUser = ticketUser;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber() {
        this.ticketNumber++;
    }
}
