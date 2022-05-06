package be.jevents.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalTime;


public class Event {

    private Long id;
    private String eventName;
    private String eventType;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate eventDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime eventTime;
    private double price;
    private Location location;


    public Event(){
        //Empty Constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName +
                ", eventType='" + eventType +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", price=" + price +
                ", location=" + location.getBuildingName() +
                '}';
    }
}


