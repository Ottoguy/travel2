package com.agency.travel2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarBookingController {

    @GetMapping("/carbooking")
    public String carBookingForm(Model model) {
        model.addAttribute("carbooking", new CarBooking());
        return "carbooking";
    }

    @PostMapping("/carbooking")
    public String carBookingSubmit(@ModelAttribute CarBooking carbooking, Model model) {
        model.addAttribute("carbooking", carbooking);
        return "result";
    }

}