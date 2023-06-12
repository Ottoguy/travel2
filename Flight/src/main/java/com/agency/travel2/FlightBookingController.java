package com.agency.travel2;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class FlightBookingController {

    @Autowired
    FlightBookingRepository flightBookingRepository;

    @GetMapping("/flightBookings")
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
    public ResponseEntity<FlightBooking> getFlightBookingById(@PathVariable("id") long id) {
        Optional<FlightBooking> flightBookingData = flightBookingRepository.findById(id);

        if (flightBookingData.isPresent()) {
            return new ResponseEntity<>(flightBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/flightBookings")
    public ResponseEntity<FlightBooking> createFlightBooking(@RequestBody FlightBooking flightBooking) {
        try {
            FlightBooking _flightBooking = flightBookingRepository
                    .save(new FlightBooking(flightBooking.getOrigin(), flightBooking.getDestination(), flightBooking.getFlightnumber()));
            return new ResponseEntity<>(_flightBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/flightBookings/{id}")
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
    public ResponseEntity<HttpStatus> deleteFlightBooking(@PathVariable("id") long id) {
        try {
            flightBookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/flightBookings")
    public ResponseEntity<HttpStatus> deleteAllFlightBookings() {
        try {
            flightBookingRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

 /*   @GetMapping("/flightBookings/published")
    public ResponseEntity<List<FlightBooking>> findByFlightnumber(int flightnumber) {
        try {
            List<FlightBooking> flightBookings = flightBookingRepository.findByFlightnumber(flightnumber);

            if (flightBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(flightBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}