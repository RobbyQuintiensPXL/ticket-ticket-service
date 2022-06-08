package be.jevents.ticketservice.events;

import be.jevents.ticketservice.model.Event;
import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketEvent {

    private int ticketId;
    private Long ticketUserId;
    private Long eventId;
    private String firstName;
    private String name;
    private String email;
    private String eventName;
    private String eventType;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private double price;
    private String buildingName;
    private int amount;

    public TicketEvent(int amount, int ticketId, Event event, TicketUser ticketUser) {
        this.ticketId = ticketId;
        this.ticketUserId = ticketUser.getId();
        this.firstName = ticketUser.getFirstName();
        this.name = ticketUser.getName();
        this.email = ticketUser.getEmail();
        this.eventId = event.getId();
        this.eventName = event.getEventName();
        this.eventType = event.getEventType();
        this.eventDate = event.getEventDate();
        this.eventTime = event.getEventTime();
        this.price = event.getPrice();
        this.buildingName = event.getLocation().getBuildingName();
        this.amount = amount;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getTicketUserId() {
        return ticketUserId;
    }

    public void setTicketUserId(Long ticketUserId) {
        this.ticketUserId = ticketUserId;
    }
}