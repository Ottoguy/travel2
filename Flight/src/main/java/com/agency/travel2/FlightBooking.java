package com.agency.travel2;

import jakarta.persistence.*;
@Entity
@Table(name = "flightBookings")
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "flightnumber")
    private int flightnumber;

    public FlightBooking() {

    }

    public FlightBooking(String origin, String destination, int flightnumber) {
        this.origin = origin;
        this.destination = destination;
        this.flightnumber = flightnumber;
    }

    public long getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFlightnumber() {
        return flightnumber;
    }

    public void setFlightnumber(int flightnumber) {
        this.flightnumber = flightnumber;
    }

    @Override
    public String toString() {
        return "Flightbooking [id=" + id + ", origin=" + origin + ", destination=" + destination + ", flightnumber=" + flightnumber + "]";
    }
}