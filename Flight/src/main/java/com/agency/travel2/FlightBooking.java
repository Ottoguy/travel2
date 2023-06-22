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

    @Column(name = "flightNumber")
    private int flightNumber;

    @Column(name = "carId")
    private long carId;

    @Column(name = "hotelId")
    private long hotelId;

    public FlightBooking() {

    }

    public FlightBooking(String origin, String destination, int flightNumber, long carId, long hotelId) {
        this.origin = origin;
        this.destination = destination;
        this.flightNumber = flightNumber;
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
        return flightNumber;
    }

    public void setFlightnumber(int flightNumber) {
        this.flightNumber = flightNumber;
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
        return "Flightbooking [id=" + id + ", origin=" + origin + ", destination=" + destination + ", flightNumber=" + flightNumber + ", carId=" + carId + ", hotelId=" + hotelId + "]";
    }
}