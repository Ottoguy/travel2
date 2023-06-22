package com.agency.travel2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class CarBookingController {

    @Autowired
    CarBookingRepository carBookingRepository;

    @GetMapping("/carbooking")
    public String carBookingForm(Model model) {
        model.addAttribute("carbooking", new CarBooking());
        return "carbooking";
    }

    @PostMapping("/carbooking")
    public String carBookingSubmit(@ModelAttribute CarBooking carbooking, Model model) {
        model.addAttribute("carbooking", carbooking);
        createCarBooking(carbooking);
        return "result";
    }

    @GetMapping("/carTable")
    @ResponseBody
    public ResponseEntity<List<CarBooking>> getAllCarBookings() {
        try {
            List<CarBooking> carTable = new ArrayList<CarBooking>();

            carBookingRepository.findAll().forEach(carTable::add);

            if (carTable.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(carTable, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<CarBooking> getCarBookingById(@PathVariable("id") long id) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            return new ResponseEntity<>(carBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getQuery")
    @ResponseBody
    public ResponseEntity<String> getQuery() {
        return new ResponseEntity<>("hej", HttpStatus.OK);
    }




    @PostMapping("/carTable")
    @ResponseBody
    public ResponseEntity<CarBooking> createCarBooking(@RequestBody CarBooking carBooking) {
        try {
            CarBooking _carBooking = carBookingRepository
                    //.save(new CarBooking(carBooking.getContent(), carBooking.getBrand(), carBooking.getFrom(), carBooking.getTo()));
                    .save(carBooking);
            return new ResponseEntity<>(_carBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<CarBooking> updateCarBooking(@PathVariable("id") long id, @RequestBody CarBooking carBooking) {
        Optional<CarBooking> carBookingData = carBookingRepository.findById(id);

        if (carBookingData.isPresent()) {
            CarBooking _carBooking = carBookingData.get();
            _carBooking.setContent(carBooking.getContent());
            _carBooking.setBrand(carBooking.getBrand());
            _carBooking.setFrom(carBooking.getFrom());
            _carBooking.setTo(carBooking.getTo());
            _carBooking.setFlightId(carBooking.getFlightId());
            _carBooking.setHotelId(carBooking.getHotelId());
            return new ResponseEntity<>(carBookingRepository.save(_carBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carTable/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteCarBooking(@PathVariable("id") long id) {
        try {
            carBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/carTable")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllCarBookings() {
        try {
            carBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}