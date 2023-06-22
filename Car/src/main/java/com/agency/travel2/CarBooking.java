package com.agency.travel2;
import jakarta.persistence.*;
@Entity
@Table(name = "carTable")
public class CarBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "content")
    private String content;

    @Column(name = "brand")
    private String brand;

    @Column(name = "from")
    private String from;

    @Column(name = "to")
    private String to;

    @Column(name = "flightId")
    private long flightId;

    @Column(name = "hotelId")
    private long hotelId;

    public CarBooking(){

    }

    public CarBooking(String content, String brand, String from, String to, long flightId, long hotelId) {
        this.content = content;
        this.brand = brand;
        this.from = from;
        this.to = to;
        this.flightId = flightId;
        this.hotelId = hotelId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Carbooking [id=" + id + ", content=" + content + ", brand=" + brand + ", from=" + from + ", to=" + to + ", flightId=" + flightId + ", hotelId=" + hotelId
                + "]";
    }

}