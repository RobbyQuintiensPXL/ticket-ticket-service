package be.jevents.ticketservice.events.model;

public class TicketChangeModel {

    private String type;
    private String action;
    private Long ticketId;
//    private String correlationId;

    public TicketChangeModel(String type, String action, Long ticketId) {
        this.type = type;
        this.action = action;
        this.ticketId = ticketId;
//        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

//    public String getCorrelationId() {
//        return correlationId;
//    }
//
//    public void setCorrelationId(String correlationId) {
//        this.correlationId = correlationId;
//    }
}
