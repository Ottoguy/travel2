package com.agency.travel2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8082")
@Controller
public class HotelBookingController {

    @Autowired
    HotelBookingRepository hotelBookingRepository;
    @Autowired
    private final RestTemplate restTemplate;
    public HotelBookingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/summary/{id}")
    @ResponseBody
    public ResponseEntity<String> summary(@PathVariable("id") long id) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        try{
            String flight_url = "http://localhost:8081/flightBookings/" + hotelBookingData.get().getFlightId();
            ResponseEntity<String> flight_response = restTemplate.getForEntity(flight_url, String.class);

            String car_url = "http://localhost:8081/flightBookings/" + hotelBookingData.get().getCarId();
            ResponseEntity<String> car_response = restTemplate.getForEntity(car_url, String.class);

                if (hotelBookingData.isPresent() ) {
                    return new ResponseEntity<>(hotelBookingData.get().toString() + flight_response.getBody().toString() + car_response.getBody().toString(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/query")
    public ResponseEntity<String> makeQuery() {
        String url = "http://localhost:8080/getQuery";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String dataResponse = response.getBody();
            // Process the dataResponse as needed
            return ResponseEntity.ok("Response from MicroserviceB: " + dataResponse);
        } else {
            // Handle error case
            return ResponseEntity.status(response.getStatusCode()).body("Error occurred: " + response.getStatusCode());
        }
    }
    @GetMapping("/hotelbooking")
    public String hotelBookingForm(Model model) {
        model.addAttribute("hotelbooking", new HotelBooking());
        return "hotelbooking";
    }

    @PostMapping("/hotelbooking")
    public String hotelBookingSubmit(@ModelAttribute HotelBooking hotelbooking, Model model) {
        model.addAttribute("hotelbooking", hotelbooking);
        createHotelBooking(hotelbooking);
        return "result";
    }

    @GetMapping("/hotelBookings")
    @ResponseBody
    public ResponseEntity<List<HotelBooking>> getAllHotelBookings() {
        try {
            List<HotelBooking> hotelBookings = new ArrayList<HotelBooking>();

            hotelBookingRepository.findAll().forEach(hotelBookings::add);

            if (hotelBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(hotelBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hotelBookings/{id}")
    @ResponseBody
    public ResponseEntity<HotelBooking> getHotelBookingById(@PathVariable("id") long id) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            return new ResponseEntity<>(hotelBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/hotelBookings")
    @ResponseBody
    public ResponseEntity<HotelBooking> createHotelBooking(@RequestBody HotelBooking hotelBooking) {
        try {
            HotelBooking _hotelBooking = hotelBookingRepository
                    .save(hotelBooking);
            return new ResponseEntity<>(_hotelBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hotelBookings/{id}")
    @ResponseBody
    public ResponseEntity<HotelBooking> updateHotelBooking(@PathVariable("id") long id, @RequestBody HotelBooking hotelBooking) {
        Optional<HotelBooking> hotelBookingData = hotelBookingRepository.findById(id);

        if (hotelBookingData.isPresent()) {
            HotelBooking _hotelBooking = hotelBookingData.get();
            _hotelBooking.setBeds(hotelBooking.getBeds());
            _hotelBooking.setFloor(hotelBooking.getFloor());
            _hotelBooking.setCarId(hotelBooking.getCarId());
            _hotelBooking.setFlightId(hotelBooking.getFlightId());
            return new ResponseEntity<>(hotelBookingRepository.save(_hotelBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/hotelBookings/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteHotelBooking(@PathVariable("id") long id) {
        try {
            hotelBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotelBookings")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllHotelBookings() {
        try {
            hotelBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}