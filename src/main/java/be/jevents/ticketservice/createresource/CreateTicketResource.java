package be.jevents.ticketservice.createresource;

public class CreateTicketResource {

    private Long eventId;

    public CreateTicketResource(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
