package be.jevents.ticketservice.createresource;

public class CreateFullTicketResource {

    private final Long eventId;

    private final String username;
    private final String name;
    private final String firstName;
    private final String street;
    private final String city;
    private final int zipCode;
    private final String country;
    private final String email;
    private final int numberOfTickets;

    public CreateFullTicketResource(Long eventId, String username, String name,
                                    String firstName, String street, String city,
                                    int zipCode, String country, String email, int numberOfTickets) {
        this.eventId = eventId;
        this.username = username;
        this.name = name;
        this.firstName = firstName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.email = email;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getUsername() {
        return username;
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

    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}
