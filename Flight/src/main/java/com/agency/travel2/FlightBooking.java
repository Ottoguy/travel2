package com.agency.travel2;

import jakarta.persistence.*;
@Entity
@Table(name = "flightTable")
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

    @Column(name = "carId")
    private long carId;

    @Column(name = "hotelId")
    private long hotelId;

    public FlightBooking() {

    }

    public FlightBooking(String origin, String destination, int flightnumber, long carId, long hotelId) {
        this.origin = origin;
        this.destination = destination;
        this.flightnumber = flightnumber;
        this.carId = carId;
        this.hotelId = hotelId;
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

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }


    @Override
    public String toString() {
        return "Flightbooking [id=" + id + ", origin=" + origin + ", destination=" + destination + ", flightnumber=" + flightnumber + ", carId=" + carId + ", hotelId=" + hotelId + "]";
    }
}