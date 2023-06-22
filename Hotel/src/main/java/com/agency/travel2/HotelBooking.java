package com.agency.travel2;

import jakarta.persistence.*;
@Entity
@Table(name = "hotelTable")
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "beds")
    private long beds;

    @Column(name = "floor")
    private long floor;

    @Column(name = "carId")
    private long carId;

    @Column(name = "flightId")
    private long flightId;

    public HotelBooking() {

    }

    public HotelBooking(long beds, long floor, long carId, long flightId) {
        this.beds = beds;
        this.floor = floor;
        this.carId = carId;
        this.flightId = flightId;
    }

    public long getId() {
        return id;
    }

    public long getBeds() {
        return beds;
    }

    public void setBeds(long beds) {
        this.beds = beds;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }
    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }


    @Override
    public String toString() {
        return "Hotelbooking [id=" + id + ", beds=" + beds + ", floor=" + floor + ", carId=" + carId + ", flightId=" + flightId + "]";
    }
}