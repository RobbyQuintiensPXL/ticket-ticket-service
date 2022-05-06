package be.jevents.ticketservice.dto;

import be.jevents.ticketservice.model.Ticket;
import be.jevents.ticketservice.model.TicketUser;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

public class TicketUserDTO {

    private final Long id;
    private final String name;
    private final String firstName;
    private final String street;
    private final String city;
    private final int zipCode;
    private final String country;
    private final String email;

    public TicketUserDTO(TicketUser ticketUser){
        this.id = ticketUser.getId();
        this.name = ticketUser.getName();
        this.firstName = ticketUser.getFirstName();
        this.street = ticketUser.getStreet();
        this.city = ticketUser.getCity();
        this.zipCode = ticketUser.getZipCode();
        this.country = ticketUser.getCountry();
        this.email = ticketUser.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }
}
