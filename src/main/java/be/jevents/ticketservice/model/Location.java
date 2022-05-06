package be.jevents.ticketservice.model;

import org.springframework.hateoas.RepresentationModel;

public class Location{

    private Long id;
    private String buildingName;
    private int zipCode;
    private String city;
    private String address;
    private String country;
    private String ticketOffice;

    public Location(){
        // Empty constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTicketOffice() {
        return ticketOffice;
    }

    public void setTicketOffice(String ticketOffice) {
        this.ticketOffice = ticketOffice;
    }
}
