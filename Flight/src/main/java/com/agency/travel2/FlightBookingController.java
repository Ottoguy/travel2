package com.agency.travel2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
public class FlightBookingController {

    @Autowired
    FlightBookingRepository flightBookingRepository;
    @Autowired
    private final RestTemplate restTemplate;
    public FlightBookingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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


    @GetMapping("/flightbooking")
    public String flightBookingForm(Model model) {
        model.addAttribute("flightbooking", new FlightBooking());
        return "flightbooking";
    }

    @PostMapping("/flightbooking")
    public String flightBookingSubmit(@ModelAttribute FlightBooking flightbooking, Model model) {
        model.addAttribute("flightbooking", flightbooking);
        createFlightBooking(flightbooking);
        return "result";
    }

    @GetMapping("/flightBookings")
    @ResponseBody
    public ResponseEntity<List<FlightBooking>> getAllFlightBookings() {
        try {
            List<FlightBooking> flightBookings = new ArrayList<FlightBooking>();

            flightBookingRepository.findAll().forEach(flightBookings::add);

            if (flightBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(flightBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flightBookings/{id}")
    @ResponseBody
    public ResponseEntity<FlightBooking> getFlightBookingById(@PathVariable("id") long id) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            return new ResponseEntity<>(flightBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/flightBookings")
    @ResponseBody
    public ResponseEntity<FlightBooking> createFlightBooking(@RequestBody FlightBooking flightBooking) {
        try {
            FlightBooking _flightBooking = flightBookingRepository
                    //.save(new FlightBooking(flightBooking.getContent(), flightBooking.getBrand(), flightBooking.getFrom(), flightBooking.getTo()));
                    .save(flightBooking);
            return new ResponseEntity<>(_flightBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/flightBookings/{id}")
    @ResponseBody
    public ResponseEntity<FlightBooking> updateFlightBooking(@PathVariable("id") long id, @RequestBody FlightBooking flightBooking) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            FlightBooking _flightBooking = flightBookingData.get();
            _flightBooking.setOrigin(flightBooking.getOrigin());
            _flightBooking.setDestination(flightBooking.getDestination());
            _flightBooking.setFlightnumber(flightBooking.getFlightnumber());
            return new ResponseEntity<>(flightBookingRepository.save(_flightBooking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/flightBookings/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteFlightBooking(@PathVariable("id") long id) {
        try {
            flightBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/flightBookings")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteAllFlightBookings() {
        try {
            flightBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}