package com.agency.travel2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarBooking {

    @RequestMapping("/booking")
    public String hello2()
    {
        return "You have reserved your " + car;
    }

    //Displays button for booking car
    @RequestMapping("/bookCar")
    public String bookCar() {
        return "<form action=\"/booking\">\n" +
                "  <input type=\"submit\" value=\"Volvo\">\n" +
                "</form>\n" +
                "<form action=\"/booking\">\n" +
                "  <input type=\"submit\" value=\"Saab\">\n" +
                "</form>\n" +
                "<form action=\"/booking\">\n" +
                "  <input type=\"submit\" value=\"Toyota\">\n" +
                "</form>";
    }
}
