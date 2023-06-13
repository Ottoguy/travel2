package com.agency.travel2;
import jakarta.persistence.*;
@Entity
@Table(name = "carBookings")
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

    public CarBooking(){

    }

    public CarBooking(String content, String brand, String from, String to) {
        this.content = content;
        this.brand = brand;
        this.from = from;
        this.to = to;
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

    @Override
    public String toString() {
        return "Carbooking [id=" + id + ", content=" + content + ", brand=" + brand + ", from=" + from + ", to=" + to
                + "]";
    }

}